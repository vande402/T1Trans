package com.transcore.connexion.sample;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.apache.axis2.AxisFault;

import com.tcore.tcoreTypes.AssetType;
import com.tcore.tcoreTypes.CountryCode;
import com.tcore.tcoreTypes.EquipmentClass;
import com.tcore.tcoreTypes.MileageType;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tcoreTypes.Zone;
import com.tcore.tfmiFreightMatching.Availability;
import com.tcore.tfmiFreightMatching.CityAndState;
import com.tcore.tfmiFreightMatching.CreateSearchOperation;
import com.tcore.tfmiFreightMatching.CreateSearchRequestDocument;
import com.tcore.tfmiFreightMatching.CreateSearchResponseDocument;
import com.tcore.tfmiFreightMatching.CreateSearchResult;
import com.tcore.tfmiFreightMatching.Dimensions;
import com.tcore.tfmiFreightMatching.FmPostalCode;
import com.tcore.tfmiFreightMatching.MatchingAsset;
import com.tcore.tfmiFreightMatching.Mileage;
import com.tcore.tfmiFreightMatching.SearchArea;
import com.tcore.tfmiFreightMatching.SearchCriteria;
import com.tcore.tfmiFreightMatching.SearchRadius;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;

/**
 * Demonstrates shipment and equipment search, showing examples of various search criteria, including several
 * ways of specifying origin and destination locations.
 * 
 * The shipmentSearchXYZ and equipmentSearchXYZ methods intentionally contain a lot of duplicated boilerplate
 * code. Most consumers will only need to look at 1 or 2 methods to find the examples required for a specific
 * use case.
 * 
 * To ensure that the searches will return matches, the application first posts several assets. For more
 * detailed examples of posting operations, see {@link PostAssets}.
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 */
public class Search extends BaseSampleClient {

    @Override
    public void run() throws RemoteException {

        try {
            // Login two users
            final SessionToken sessionToken1 = loginUser1();
            final SessionToken sessionToken2 = loginUser2();

            // Clean up any assets left over from previous runs
            deleteAllAssets(sessionToken1);

            System.out.println("======= Shipment Search =======");
            System.out.println("======= Posting =======");
            postShipment(sessionToken1, "Salina", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f);
            postShipment(sessionToken1, "Manhattan", StateProvince.KS, "Teller", StateProvince.AK, 65.25f,
                    -166.35f);
            postShipment(sessionToken1, "Wichita", StateProvince.KS, "White Mountain", StateProvince.AK,
                    64.681f, -163.44f);
            postShipment(sessionToken1, "Kansas City", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f);

            System.out.println("\n======= Searching for shipments (postal code -> city/state) =======");
            shipmentSearchPostalCode2CityState(sessionToken2);

            System.out.println("\n======= Searching for shipments (state -> zone =======");
            shipmentSearchState2Zone(sessionToken2);

            System.out.println("\n\n\n======= Equipment Search =======");
            System.out.println("======= Posting =======");
            postEquipment(sessionToken1, "Salina", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f, false);
            postEquipment(sessionToken1, "Manhattan", StateProvince.KS, "Teller", StateProvince.AK, 65.25f,
                    -166.35f, false);
            postEquipment(sessionToken1, "Wichita", StateProvince.KS, "White Mountain", StateProvince.AK,
                    64.681f, -163.44f, false);
            postEquipment(sessionToken1, "Kansas City", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f, false);

            System.out.println("\n======= Searching for equipment (postal code -> city/state) =======");
            equipmentSearchPostalCode2CityState(sessionToken2);

            System.out.println("\n======= Searching for equipment (state -> zone =======");
            equipmentSearchState2Zone(sessionToken2);

            // Clean up - delete all assets.
            //
            // Note: searches can be deleted as well, but there is generally no
            // advantage to deleting them unless we plan to look up recent
            // searches later and want to eliminate certain searches from the
            // list returned from that lookup.
            deleteAllAssets(sessionToken1);

        } catch (final AxisFault fault) {
            System.err.println(fault.getMessage() + " : " + fault.getDetail().getText());
            fault.printStackTrace();
        }
    }

    /**
     * Searches for shipments, specifying origin by postal code and destination by city/state.
     * 
     * @throws RemoteException
     */
    protected void shipmentSearchPostalCode2CityState(final SessionToken sessionToken) throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.SHIPMENT);

        // We posted EquipmentType.AUTO_CARRIER, which is in the class
        // EquipmentClass.OTHER_EQUIPMENT
        criteria.addEquipmentClasses(EquipmentClass.OTHER_EQUIPMENT);
        // Searches can specify arbitrarily many equipment classes. We'll add
        // one additional class.
        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);

        // Age limit is normally 120 (2 hours back), 240 (4 hours back), or
        // more, but we just posted the assets we expect to see, so we'll
        // specify a very short age limit (2 minutes).
        criteria.setAgeLimitMinutes(2);

        // Origin - by postal code
        final SearchRadius origin = criteria.addNewOrigin().addNewRadius();
        final FmPostalCode originPlace = origin.addNewPlace().addNewPostalCode();
        originPlace.setCode("67401");
        originPlace.setCountry(CountryCode.US);

        // Origin radius
        final Mileage originRadius = origin.addNewRadius();
        originRadius.setMiles(200);
        originRadius.setMethod(MileageType.ROAD);

        // Destination - by city/state
        final SearchRadius destination = criteria.addNewDestination().addNewRadius();
        final CityAndState destinationPlace = destination.addNewPlace().addNewCityAndState();
        destinationPlace.setCity("Nome");
        destinationPlace.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destinationPlace.setCounty("Nome");

        // Destination radius
        final Mileage destinationRadius = destination.addNewRadius();
        destinationRadius.setMiles(100);
        destinationRadius.setMethod(MileageType.ROAD);

        // Length and weight (optional)
        final Dimensions d = criteria.addNewLimits();
        d.setLengthFeet(55);
        d.setWeightPounds(55000);

        // Availability (optional). We're interested in assets available from 4
        // hours from now until 48 hours from now.
        final Availability availability = criteria.addNewAvailability();
        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.HOUR_OF_DAY, 4);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 2);
        availability.setLatest(latest);

        // Validate the request document before executing the operation
        validate(searchRequestDoc);

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final CreateSearchResult result = responseDoc.getCreateSearchResponse().getCreateSearchResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetCreateSearchSuccessData()) {
            throw new RemoteException("Search Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output match count and summaries
        System.out.println(result.getCreateSearchSuccessData().getTotalMatches() + " matches found");
        for (final MatchingAsset match : result.getCreateSearchSuccessData().getMatchesArray()) {
            System.out.println(summaryString(match));
        }
    }

    /**
     * Searches for shipments, specifying origin by state and destination by zone.
     * 
     * @throws Exception
     */
    protected void shipmentSearchState2Zone(final SessionToken sessionToken) throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.SHIPMENT);

        // We posted EquipmentType.AUTO_CARRIER, which is in the class
        // EquipmentClass.OTHER_EQUIPMENT
        criteria.addEquipmentClasses(EquipmentClass.OTHER_EQUIPMENT);
        // Searches can specify arbitrarily many equipment classes. We'll add
        // one additional class.
        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);

        // Age limit is normally 120 (2 hours back), 240 (4 hours back), or
        // more, but we just posted the assets we expect to see, so we'll
        // specify a very short age limit (2 minutes).
        criteria.setAgeLimitMinutes(2);

        // Origin - by state. Note - if desired, we can add arbitrarily many
        // states
        final SearchArea origin = criteria.addNewOrigin().addNewArea();
        origin.addStateProvinces(StateProvince.KS);

        // Destination - by zone. Note - if desired, we can add arbitrarily many
        // zones
        final SearchArea destination = criteria.addNewDestination().addNewArea();
        destination.addZones(Zone.WEST);

        // Length and weight (optional)
        final Dimensions d = criteria.addNewLimits();
        d.setLengthFeet(55);
        d.setWeightPounds(55000);

        // Availability (optional). We're interested in assets available from 4
        // hours from now until 48 hours from now.
        final Availability availability = criteria.addNewAvailability();
        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.HOUR_OF_DAY, 4);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 2);
        availability.setLatest(latest);

        // Validate the request document before executing the operation
        validate(searchRequestDoc);

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final CreateSearchResult result = responseDoc.getCreateSearchResponse().getCreateSearchResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetCreateSearchSuccessData()) {
            throw new RemoteException("Search Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output match count and summaries
        System.out.println(result.getCreateSearchSuccessData().getTotalMatches() + " matches found");
        for (final MatchingAsset match : result.getCreateSearchSuccessData().getMatchesArray()) {
            System.out.println(summaryString(match));
        }
    }

    /**
     * Searches for equipment, specifying origin by postal code and destination by city/state.
     * 
     * @throws Exception
     */
    protected void equipmentSearchPostalCode2CityState(final SessionToken sessionToken)
            throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.EQUIPMENT);

        // We posted EquipmentType.AUTO_CARRIER, which is in the class
        // EquipmentClass.OTHER_EQUIPMENT
        criteria.addEquipmentClasses(EquipmentClass.OTHER_EQUIPMENT);
        // Searches can specify arbitrarily many equipment classes. We'll add
        // one additional class.
        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);

        // Age limit is normally 120 (2 hours back), 240 (4 hours back), or
        // more, but we just posted the assets we expect to see, so we'll
        // specify a very short age limit (2 minutes).
        criteria.setAgeLimitMinutes(2);

        // Origin - by postal code
        final SearchRadius origin = criteria.addNewOrigin().addNewRadius();
        final FmPostalCode originPlace = origin.addNewPlace().addNewPostalCode();
        originPlace.setCode("67401");
        originPlace.setCountry(CountryCode.US);

        // Origin radius
        final Mileage originRadius = origin.addNewRadius();
        originRadius.setMiles(200);
        originRadius.setMethod(MileageType.ROAD);

        // Destination - by city/state
        final SearchRadius destination = criteria.addNewDestination().addNewRadius();
        final CityAndState destinationPlace = destination.addNewPlace().addNewCityAndState();
        destinationPlace.setCity("Nome");
        destinationPlace.setStateProvince(StateProvince.AK);
        // Specifying county is optional, but ensures precise location
        // resolution in case a state contains multiple cities of the same name.
        destinationPlace.setCounty("Nome");

        // Destination radius
        final Mileage destinationRadius = destination.addNewRadius();
        destinationRadius.setMiles(100);
        destinationRadius.setMethod(MileageType.ROAD);

        // Length and weight (optional)
        final Dimensions d = criteria.addNewLimits();
        d.setLengthFeet(40);
        d.setWeightPounds(40000);

        // Availability (optional). We're interested in assets available from 4
        // hours from now until 48 hours from now.
        final Availability availability = criteria.addNewAvailability();
        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.HOUR_OF_DAY, 4);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 2);
        availability.setLatest(latest);

        // Validate the request document before executing the operation
        validate(searchRequestDoc);

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final CreateSearchResult result = responseDoc.getCreateSearchResponse().getCreateSearchResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetCreateSearchSuccessData()) {
            throw new RemoteException("Search Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output match count and summaries
        System.out.println(result.getCreateSearchSuccessData().getTotalMatches() + " matches found");
        for (final MatchingAsset match : result.getCreateSearchSuccessData().getMatchesArray()) {
            System.out.println(summaryString(match));
        }
    }

    /**
     * Searches for equipment, specifying origin by state and destination by zone.
     * 
     * @throws RemoteException
     */
    protected void equipmentSearchState2Zone(final SessionToken sessionToken) throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.EQUIPMENT);

        // We posted EquipmentType.AUTO_CARRIER, which is in the class
        // EquipmentClass.OTHER_EQUIPMENT
        criteria.addEquipmentClasses(EquipmentClass.OTHER_EQUIPMENT);
        // Searches can specify arbitrarily many equipment classes. We'll add
        // one additional class.
        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);

        // Age limit is normally 120 (2 hours back), 240 (4 hours back), or
        // more, but we just posted the assets we expect to see, so we'll
        // specify a very short age limit (2 minutes).
        criteria.setAgeLimitMinutes(2);

        // Origin - by state. Note - if desired, we can add arbitrarily many
        // states
        final SearchArea origin = criteria.addNewOrigin().addNewArea();
        origin.addStateProvinces(StateProvince.KS);

        // Destination - by zone. Note - if desired, we can add arbitrarily many
        // zones
        final SearchArea destination = criteria.addNewDestination().addNewArea();
        destination.addZones(Zone.WEST);

        // Length and weight (optional)
        final Dimensions d = criteria.addNewLimits();
        d.setLengthFeet(40);
        d.setWeightPounds(40000);

        // Availability (optional). We're interested in assets available from 4
        // hours from now until 48 hours from now.
        final Availability availability = criteria.addNewAvailability();
        final Calendar earliest = Calendar.getInstance();
        earliest.add(Calendar.HOUR_OF_DAY, 4);
        availability.setEarliest(earliest);

        final Calendar latest = Calendar.getInstance();
        latest.add(Calendar.DATE, 2);
        availability.setLatest(latest);

        // Validate the request document before executing the operation
        validate(searchRequestDoc);

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final CreateSearchResult result = responseDoc.getCreateSearchResponse().getCreateSearchResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetCreateSearchSuccessData()) {
            throw new RemoteException("Search Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output match count and summaries
        System.out.println(result.getCreateSearchSuccessData().getTotalMatches() + " matches found");
        for (final MatchingAsset match : result.getCreateSearchSuccessData().getMatchesArray()) {
            System.out.println(summaryString(match));
        }
    }

    public static void main(final String[] args) {
        run(args);
    }
}
