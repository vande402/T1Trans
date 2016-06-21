package com.transcore.connexion.sample;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.apache.axis2.AxisFault;

import com.tcore.tcoreTypes.CountryCode;
import com.tcore.tcoreTypes.EquipmentType;
import com.tcore.tcoreTypes.MileageType;
import com.tcore.tcoreTypes.PostalCode;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tcoreTypes.Zone;
import com.tcore.tfmiFreightMatching.AlarmSearchCriteria;
import com.tcore.tfmiFreightMatching.Asset;
import com.tcore.tfmiFreightMatching.Availability;
import com.tcore.tfmiFreightMatching.CityAndState;
import com.tcore.tfmiFreightMatching.Dimensions;
import com.tcore.tfmiFreightMatching.Equipment;
import com.tcore.tfmiFreightMatching.FmPostalCode;
import com.tcore.tfmiFreightMatching.LatLon;
import com.tcore.tfmiFreightMatching.LookupAssetOperation;
import com.tcore.tfmiFreightMatching.LookupAssetRequestDocument;
import com.tcore.tfmiFreightMatching.LookupAssetResponseDocument;
import com.tcore.tfmiFreightMatching.LookupAssetResult;
import com.tcore.tfmiFreightMatching.Mileage;
import com.tcore.tfmiFreightMatching.NamedLatLon;
import com.tcore.tfmiFreightMatching.NamedPostalCode;
import com.tcore.tfmiFreightMatching.PostAssetOperation;
import com.tcore.tfmiFreightMatching.PostAssetRequestDocument;
import com.tcore.tfmiFreightMatching.PostAssetResponseDocument;
import com.tcore.tfmiFreightMatching.PostAssetResult;
import com.tcore.tfmiFreightMatching.RateBasedOnType;
import com.tcore.tfmiFreightMatching.Shipment;
import com.tcore.tfmiFreightMatching.ShipmentRate;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;

/**
 * Demonstrates posting shipments and equipment, showing examples of various posting attributes, including
 * several ways of specifying origin and destination locations.
 * 
 * The postXYZ methods intentionally contain a lot of duplicated boilerplate code. Most consumers will only
 * need to look at 1 or 2 posting methods to find the examples required for a specific use case.
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 */
public class PostAssets extends BaseSampleClient {

    @Override
    public void run() throws RemoteException {

        try {
            // Login
            final SessionToken sessionToken = loginUser1();

            // Clean up any assets left over from previous runs
            deleteAllAssets(sessionToken);

            System.out.println("======= Posting equipment =======");

            // Post a piece of equipment, specifying origin by postal code and
            // destination by city/state
            postEquipmentPostalCode2CityState(sessionToken);

            // Post a piece of equipment, specifying origin by 'named postal
            // code' (postal code + city/state) and destination by zone
            postEquipmentNamedPostalCode2AreaZone(sessionToken);

            // Post a piece of equipment, specifying origin by lat/lon and
            // destination as open
            postEquipmentLatitudeLongitude2OpenSetOptionalItems(sessionToken);

            // Post a piece of equipment, specifying origin by postal code and
            // destination by 'named lat lon' (latitude/longitude/city/state)
            postEquipmentPostalCode2NamedLatLon(sessionToken);

            System.out.println("\n======= Posting shipments =======");

            // Post a shipment, specifying origin by postal code and destination
            // by city/state
            postShipmentPostalCode2CityStateProvince(sessionToken);

            // Post a shipment, specifying origin by 'named postal code' (postal
            // code + city/state) and destination by lat/lon
            postShipmentNamedPostalCode2LatitudeLongitude(sessionToken);

            // Post a shipment, specifying origin and destination by 'named lat
            // lon' (latitude/longitude/city/state) and including optional
            // information such as rates, truckstops, etc.
            postShipmentNamedLatLon2CityStateSetOptionalItems(sessionToken);

            // Post a shipment, specifying origin and destination
            postShipmentSetTruckstopsAsClosestSetRates(sessionToken);

            System.out.println("\n======= Looking up assets =======");

            // Lookup the assets we just posted and print summaries
            queryMyAssets(sessionToken);

            // Clean up
            deleteAllAssets(sessionToken);
        } catch (final AxisFault fault) {
            System.err.println(fault.getMessage() + " : " + fault.getDetail().getText());
            fault.printStackTrace();
        }
    }

    /**
     * Posts a truck, specifying the origin by postal code and the destination by city/state.
     * 
     * @throws RemoteException
     */
    protected void postEquipmentPostalCode2CityState(final SessionToken sessionToken) throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Equipment equipment = operation.addNewEquipment();
        equipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final FmPostalCode origin = equipment.addNewOrigin().addNewPostalCode();
        origin.setCode("67401");
        origin.setCountry(CountryCode.US);

        // Destination - by city/state
        final CityAndState destination = equipment.addNewDestination().addNewPlace().addNewCityAndState();
        destination.setCity("Nome");
        destination.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destination.setCounty("Nome");

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        operation.setPostersReferenceId("eq_pc2cs");

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        // We only posted a single asset. If we posted multiple assets in the
        // same request, we would instead call getPostAssetResultsArray() and
        // receive an array of PostAssetResult objects
        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted equipment from PostalCode to City/State; AssetId = " + assetId);
    }

    /**
     * Posts a truck, specifying the origin by named postal code and the destination as a zone.
     * 
     * @throws RemoteException
     */
    protected void postEquipmentNamedPostalCode2AreaZone(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Equipment equipment = operation.addNewEquipment();
        equipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by named postal code
        final NamedPostalCode origin = equipment.addNewOrigin().addNewNamedPostalCode();
        origin.setCity("Salina");
        origin.setStateProvince(StateProvince.KS);
        final PostalCode postalCode = origin.addNewPostalCode();
        postalCode.setCode("67401");
        postalCode.setCountry(CountryCode.US);

        // Destination zone
        equipment.addNewDestination().addNewArea().addZones(Zone.WEST);

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        operation.setPostersReferenceId("eq_npc2z");

        // Post the asset
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted equipment from NamedPostalCode to Zone; AssetId = " + assetId);
    }

    /**
     * Posts a truck, specifying the origin by postal code and the destination by 'named lat lon'
     * (latitude/longitude/city/state).
     * 
     * @throws RemoteException
     */
    protected void postEquipmentPostalCode2NamedLatLon(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Equipment equipment = operation.addNewEquipment();
        equipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final FmPostalCode origin = equipment.addNewOrigin().addNewPostalCode();
        origin.setCode("67401");
        origin.setCountry(CountryCode.US);

        // Destination - by named lat lon
        final NamedLatLon destination = equipment.addNewDestination().addNewPlace().addNewNamedCoordinates();
        destination.setLatitude(64.500f);
        destination.setLongitude(-165.404f);
        destination.setCity("Nome");
        destination.setStateProvince(StateProvince.AK);

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        operation.setPostersReferenceId("eq_pc2nl");

        // Post the asset
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted equipment from PostalCode to NamedLatLon; AssetId = " + assetId);
    }

    /**
     * Posts a truck, specifying the origin by lat/lon and the destination as 'open' (anywhere).
     * 
     * Sets a 100-mile-radius alarm on this posting
     * 
     * @throws RemoteException
     */
    protected void postEquipmentLatitudeLongitude2OpenSetOptionalItems(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Equipment equipment = operation.addNewEquipment();
        equipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final LatLon origin = equipment.addNewOrigin().addNewCoordinates();
        origin.setLatitude(38.8398f);
        origin.setLongitude(-97.6095f);

        // Destination - open
        equipment.addNewDestination().addNewOpen();

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(25);
        d.setWeightPounds(25000);

        operation.setPostersReferenceId("eq_ll2o");

        // Set optional LTL
        operation.setLtl(true);

        // Set optional Count (# of identical pieces of equipment represented by
        // this posting)
        operation.setCount(3);

        // Set optional availability - available from tomorrow until 1 week from
        // now
        final Availability availability = operation.addNewAvailability();

        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.DATE, 7);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 7);
        availability.setLatest(latest);

        // Set a 100-mile-radius alarm on this asset
        final AlarmSearchCriteria alarmSearchCriteria = operation.addNewAlarm();

        // Origin radius
        final Mileage originRadius = alarmSearchCriteria.addNewOriginRadius();
        originRadius.setMethod(MileageType.ROAD);
        originRadius.setMiles(100);

        // Destination radius is similar (although it will be ignored in this
        // case, since the asset's destination is open)
        final Mileage destinationRadius = alarmSearchCriteria.addNewDestinationRadius();
        destinationRadius.setMethod(MileageType.ROAD);
        destinationRadius.setMiles(100);

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        // Post the asset
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        final String alarmId = result.getPostAssetSuccessData().getAlarmId();
        System.out.println("Posted equipment from Coordinates to Open; AssetId = " + assetId + " AlarmId = "
                + alarmId);
    }

    /**
     * Posts a shipment, setting the origin by postal code and the destination by city/state.
     * 
     * @throws RemoteException
     */
    protected void postShipmentPostalCode2CityStateProvince(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Shipment shipment = operation.addNewShipment();
        shipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final FmPostalCode origin = shipment.addNewOrigin().addNewPostalCode();
        origin.setCode("67401");
        origin.setCountry(CountryCode.US);

        // Destination - by city/state
        final CityAndState destination = shipment.addNewDestination().addNewCityAndState();
        destination.setCity("Nome");
        destination.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destination.setCounty("Nome");

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        operation.setPostersReferenceId("sh_pc2cs");

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        // We only posted a single asset. If we posted multiple assets in the
        // same request, we would instead call getPostAssetResultsArray() and
        // receive an array of PostAssetResult objects
        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted shipment from PostalCode to City/State; AssetId = " + assetId);
    }

    /**
     * Posts a shipment.
     * <p>
     * Sets the origin by NamedPostalCode (city and state or province plus postal code).
     * <p>
     * Sets the destination by latitude and longitude.
     * 
     * @throws RemoteException
     */
    protected void postShipmentNamedPostalCode2LatitudeLongitude(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Shipment shipment = operation.addNewShipment();
        shipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by named postal code
        final NamedPostalCode origin = shipment.addNewOrigin().addNewNamedPostalCode();
        origin.setCity("Salina");
        origin.setStateProvince(StateProvince.KS);
        final PostalCode postalCode = origin.addNewPostalCode();
        postalCode.setCode("67401");
        postalCode.setCountry(CountryCode.US);

        // Destination - by lat/lon
        final LatLon destination = shipment.addNewDestination().addNewCoordinates();
        destination.setLatitude(64.500f);
        destination.setLongitude(-165.404f);

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        operation.setPostersReferenceId("sh_npc2c");

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        // Post the asset
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted shipment from NamedPostalCode to Lat/Lon; AssetId = " + assetId);
    }

    /**
     * Posts a shipment.
     * <p>
     * Sets the origin by NamedLatLon (city and state or province plus latitude and longitude).
     * <p>
     * Sets the destination by city and state.
     * <p>
     * Sets all optional items: LTL, PostersReferenceId, Count, Dimensions, Availability, and alarm.
     * <p>
     * For dimensions, feet and pounds are more commonly used; cubic feet volume and pounds weight less
     * commonly used.
     * 
     * @throws RemoteException
     */
    protected void postShipmentNamedLatLon2CityStateSetOptionalItems(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Shipment shipment = operation.addNewShipment();
        shipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final NamedLatLon origin = shipment.addNewOrigin().addNewNamedCoordinates();
        origin.setLatitude(38.8398f);
        origin.setLongitude(-97.6095f);
        origin.setCity("Salina");
        origin.setStateProvince(StateProvince.KS);

        // Destination - by city/state
        final CityAndState destination = shipment.addNewDestination().addNewCityAndState();
        destination.setCity("Nome");
        destination.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destination.setCounty("Nome");

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(25);
        d.setWeightPounds(25000);

        operation.setPostersReferenceId("sh_nl2cs");

        // Set optional LTL
        operation.setLtl(true);

        // Set optional Count (# of identical pieces of equipment represented by
        // this posting)
        operation.setCount(3);

        // Set optional availability - available from tomorrow until 1 week from
        // now
        final Availability availability = operation.addNewAvailability();

        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.DATE, 7);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 7);
        availability.setLatest(latest);

        // Set a 100-mile-radius alarm on this asset
        final AlarmSearchCriteria alarmSearchCriteria = operation.addNewAlarm();

        // Origin radius
        final Mileage originRadius = alarmSearchCriteria.addNewOriginRadius();
        originRadius.setMethod(MileageType.ROAD);
        originRadius.setMiles(100);

        // Destination radius
        final Mileage destinationRadius = alarmSearchCriteria.addNewDestinationRadius();
        destinationRadius.setMethod(MileageType.ROAD);
        destinationRadius.setMiles(100);

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        // Post the asset
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        final String alarmId = result.getPostAssetSuccessData().getAlarmId();
        System.out.println("Posted shipment from NamedLatLon to City/State; AssetId = " + assetId
                + " AlarmId = " + alarmId);
    }

    /**
     * Posts a shipment, specifying origin and destination by city/state, and including posting to the DAT
     * truck-stop network and setting rates.
     * 
     * @throws RemoteException
     */
    protected void postShipmentSetTruckstopsAsClosestSetRates(final SessionToken sessionToken)
            throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Shipment shipment = operation.addNewShipment();
        shipment.setEquipmentType(EquipmentType.VAN);

        // Origin - by postal code
        final CityAndState origin = shipment.addNewOrigin().addNewCityAndState();
        origin.setCity("Salina");
        origin.setStateProvince(StateProvince.KS);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        origin.setCounty("Saline");

        // Destination - by city/state
        final CityAndState destination = shipment.addNewDestination().addNewCityAndState();
        destination.setCity("Nome");
        destination.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destination.setCounty("Nome");

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(52);
        d.setWeightPounds(50000);

        // Post to closest truck-stops. See TfmiFreightMatching.xsd for
        // documentation
        // on specifying truck-stops by ID or by 'alternate closest'
        shipment.addNewTruckStops().addNewClosest();

        // Rate
        final ShipmentRate rate = shipment.addNewRate();
        rate.setBaseRateDollars(1.25f);
        rate.setRateBasedOn(RateBasedOnType.PER_MILE);
        rate.setRateMiles(5000);

        operation.setPostersReferenceId("sh_cs2cs");

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        // We only posted a single asset. If we posted multiple assets in the
        // same request, we would instead call getPostAssetResultsArray() and
        // receive an array of PostAssetResult objects
        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        final String assetId = result.getPostAssetSuccessData().getAssetId();
        System.out.println("Posted shipment from City/State to City/State; AssetId = " + assetId);
    }

    /**
     * Queries my own assets and prints out summaries to STDOUT.
     * 
     * @throws RemoteException
     */
    public void queryMyAssets(final SessionToken sessionToken) throws RemoteException {

        final LookupAssetRequestDocument lookupRequestDoc = LookupAssetRequestDocument.Factory.newInstance();
        final LookupAssetOperation operation = lookupRequestDoc.addNewLookupAssetRequest()
                .addNewLookupAssetOperation();

        operation.addNewQueryAllMyAssets();

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupAssetResponseDocument responseDoc = stub.lookupAsset(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupAssetResult result = responseDoc.getLookupAssetResponse().getLookupAssetResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output summaries of returned assets
        for (final Asset asset : result.getLookupAssetSuccessData().getAssetsArray()) {
            System.out.println(summaryString(asset));
        }
    }

    public static void main(final String[] args) {
        run(args);
    }
}
