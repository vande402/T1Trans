package com.transcore.connexion.sample;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.axis2.AxisFault;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import cltool4j.BaseCommandlineTool;
import cltool4j.GlobalConfigProperties;

import com.tcore.tcoreHeaders.SessionHeaderDocument;
import com.tcore.tcoreTypes.EquipmentType;
import com.tcore.tcoreTypes.MileageType;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tfmiFreightMatching.AlarmSearchCriteria;
import com.tcore.tfmiFreightMatching.Asset;
import com.tcore.tfmiFreightMatching.CityAndState;
import com.tcore.tfmiFreightMatching.DeleteAssetOperation;
import com.tcore.tfmiFreightMatching.DeleteAssetRequestDocument;
import com.tcore.tfmiFreightMatching.DeleteAssetResponseDocument;
import com.tcore.tfmiFreightMatching.DeleteAssetResult;
import com.tcore.tfmiFreightMatching.Dimensions;
import com.tcore.tfmiFreightMatching.Equipment;
import com.tcore.tfmiFreightMatching.LoginOperation;
import com.tcore.tfmiFreightMatching.LoginRequestDocument;
import com.tcore.tfmiFreightMatching.LoginResponseDocument;
import com.tcore.tfmiFreightMatching.LoginResult;
import com.tcore.tfmiFreightMatching.MatchingAsset;
import com.tcore.tfmiFreightMatching.Mileage;
import com.tcore.tfmiFreightMatching.NamedLatLon;
import com.tcore.tfmiFreightMatching.Place;
import com.tcore.tfmiFreightMatching.PostAssetOperation;
import com.tcore.tfmiFreightMatching.PostAssetRequestDocument;
import com.tcore.tfmiFreightMatching.PostAssetResponseDocument;
import com.tcore.tfmiFreightMatching.PostAssetResult;
import com.tcore.tfmiFreightMatching.Shipment;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;

/**
 * This class provides functionality that is common to all sample clients, including login and cleanup
 * (deleting assets).
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 * 
 * Note that all sample applications make use of the cltool4j command-line framework available at
 * http://code.google.com/p/cltool4j/.
 */
public abstract class BaseSampleClient extends BaseCommandlineTool {

    protected String endpointUrl;
    protected String secureEndpointUrl;

    protected final Random random = new Random();

    @Override
    public void setup() {
        endpointUrl = GlobalConfigProperties.singleton().getProperty("url");
        secureEndpointUrl = GlobalConfigProperties.singleton().getProperty("secureUrl");
    }

    protected SessionToken login(final String loginId, final String password) throws RemoteException {
        final LoginRequestDocument loginRequestDoc = LoginRequestDocument.Factory.newInstance();
        final LoginOperation operation = loginRequestDoc.addNewLoginRequest().addNewLoginOperation();
        operation.setLoginId(loginId);
        operation.setPassword(password);
        operation.setThirdPartyId("SampleClient");

        // Validate the request document before executing the operation
        validate(loginRequestDoc);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LoginResponseDocument responseDoc = stub.login(loginRequestDoc, null, null, null);

        final LoginResult result = responseDoc.getLoginResponse().getLoginResult();

        if (!result.isSetLoginSuccessData()) {
            throw new IllegalArgumentException("Authenticaton failure using credentials: " + loginId + ","
                    + password);
        }
        return result.getLoginSuccessData().getToken();
    }

    protected SessionToken loginUser1() throws RemoteException {
        return login(GlobalConfigProperties.singleton().getProperty("loginId1"), GlobalConfigProperties
                .singleton().getProperty("password1"));
    }

    protected SessionToken loginUser2() throws RemoteException {
        return login(GlobalConfigProperties.singleton().getProperty("loginId2"), GlobalConfigProperties
                .singleton().getProperty("password2"));
    }

    protected SessionToken loginDob() throws RemoteException {
        return login(GlobalConfigProperties.singleton().getProperty("loginDob"), GlobalConfigProperties
                .singleton().getProperty("passwordDob"));
    }

    protected SessionHeaderDocument sessionHeaderDocument(final SessionToken sessionToken) {
        final SessionHeaderDocument doc = SessionHeaderDocument.Factory.newInstance();
        doc.addNewSessionHeader().setSessionToken(sessionToken);
        return doc;
    }

    /**
     * Validates an XMLBeans object (generally a request document) and throws an AxisFault if validation fails
     * 
     * @param xmlObject
     * @throws AxisFault
     */
    protected void validate(final XmlObject xmlObject) throws AxisFault {

        final XmlOptions validateOptions = new XmlOptions();
        final ArrayList<XmlError> errorList = new ArrayList<XmlError>();
        validateOptions.setErrorListener(errorList);

        try {
            if (!xmlObject.validate(validateOptions)) {
                final StringBuffer sb = new StringBuffer(256);

                for (final XmlError error : errorList) {
                    sb.append("Line: " + error.getCursorLocation().xmlText() + " : " + error.getMessage()
                            + "\n");
                }

                throw new AxisFault("Invalid Object: \n" + sb.toString());
            }
        } catch (final Exception e) {
            throw new AxisFault(e.getMessage());
        }

    }

    /**
     * Deletes all assets owned by the user. Note that deleting an asset also deletes the alarm associated
     * with that asset (if any) and that searches do not need to be deleted.
     * 
     * @throws Exception
     */
    protected void deleteAllAssets(final SessionToken sessionToken) throws RemoteException {
        final DeleteAssetRequestDocument deleteRequestDoc = DeleteAssetRequestDocument.Factory.newInstance();
        final DeleteAssetOperation operation = deleteRequestDoc.addNewDeleteAssetRequest()
                .addNewDeleteAssetOperation();

        // Delete all the user's assets. If so desired, we could delete by
        // AssetId or PostersReferenceId or delete all assets owned by the
        // user's sharing group, using the alternative code commented out below
        operation.addNewDeleteAllMyAssets();

        // DeleteAssetsByAssetIds deleteByAssetIds =
        // operation.addNewDeleteAssetsByAssetIds();
        // deleteByAssetIds.addAssetIds("TS000AAA");
        // deleteByAssetIds.addAssetIds("TS000BBB");
        //
        // DeleteAssetByPostersReferenceId deleteByReferenceId =
        // operation.addNewDeleteAssetByPostersReferenceId();
        // deleteByReferenceId.setPostersReferenceId("RefId");
        //
        // operation.addNewDeleteAllMyGroupsAssets();

        // Validate the request document before executing the operation
        validate(deleteRequestDoc);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final DeleteAssetResponseDocument responseDoc = stub.deleteAsset(deleteRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));

        final DeleteAssetResult result = responseDoc.getDeleteAssetResponse().getDeleteAssetResult();

        if (!result.isSetDeleteAssetSuccessData()) {
            throw new RemoteException("Delete Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }
    }

    /**
     * Returns a simple summary of the asset.
     * 
     * @param asset
     * @return Simple summary of the asset
     */
    public static String summaryString(final Asset asset) {
        final Place originPlace = asset.isSetShipment() ? asset.getShipment().getOrigin() : asset
                .getEquipment().getOrigin();
        final String originString = originPlace.getNamedCoordinates().getCity() + ","
                + originPlace.getNamedCoordinates().getStateProvince().toString();

        String destinationString;
        if (asset.isSetShipment()) {
            final Place destPlace = asset.getShipment().getDestination();
            destinationString = destPlace.getNamedCoordinates().getCity() + ","
                    + destPlace.getNamedCoordinates().getStateProvince().toString();
        } else {
            if (asset.getEquipment().getDestination().isSetPlace()) {

                final Place destPlace = asset.getEquipment().getDestination().getPlace();
                destinationString = destPlace.getNamedCoordinates().getCity() + ","
                        + destPlace.getNamedCoordinates().getStateProvince().toString();

            } else if (asset.getEquipment().getDestination().isSetArea()) {

                final StateProvince.Enum[] states = asset.getEquipment().getDestination().getArea()
                        .getStateProvincesArray();
                final StringBuilder sb = new StringBuilder();
                sb.append(states[0]);
                for (int i = 1; i < states.length; ++i) {
                    sb.append(',').append(states[i]);
                }
                destinationString = sb.toString();

            } else {
                destinationString = "Open";
            }
        }

        return String.format("%s %s -> %s", asset.getAssetId(), originString, destinationString);
    }

    /**
     * Returns a simple summary of the match.
     * 
     * @param match
     * @return Simple summary of the match
     */
    public static String summaryString(final MatchingAsset match) {
        final String originDeadhead = match.isSetOriginDeadhead() ? Integer.toString(match
                .getOriginDeadhead().getMiles()) : "-";
        final String destinationDeadhead = match.isSetDestinationDeadhead() ? Integer.toString(match
                .getDestinationDeadhead().getMiles()) : "-";
        return String.format("%s (%s, %s)", summaryString(match.getAsset()), originDeadhead,
                destinationDeadhead);
    }

    /**
     * Posts a sample shipment.
     */
    protected PostAssetResult postShipment(final SessionToken sessionToken, final String originCity,
            final StateProvince.Enum originState, final String destinationCity,
            final StateProvince.Enum destinationState, final float destinationLatitude,
            final float destinationLongitude) throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Shipment shipment = operation.addNewShipment();
        shipment.setEquipmentType(EquipmentType.AUTO_CARRIER);

        final CityAndState origin = shipment.addNewOrigin().addNewCityAndState();
        origin.setCity(originCity);
        origin.setStateProvince(originState);

        // Destination - by city/state/lat/lon
        final NamedLatLon destination = shipment.addNewDestination().addNewNamedCoordinates();
        destination.setCity(destinationCity);
        destination.setStateProvince(destinationState);
        destination.setLatitude(destinationLatitude);
        destination.setLongitude(destinationLongitude);

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(40 + random.nextInt(5));
        d.setWeightPounds(40000 + random.nextInt(5000));

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        // Post
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }
        return result;
    }

    /**
     * Posts a sample equipment posting.
     */
    protected void postEquipment(final SessionToken sessionToken, final String originCity,
            final StateProvince.Enum originState, final String destinationCity,
            final StateProvince.Enum destinationState, final float destinationLatitude,
            final float destinationLongitude, final boolean setAlarm) throws RemoteException {

        final PostAssetRequestDocument postRequestDoc = PostAssetRequestDocument.Factory.newInstance();
        final PostAssetOperation operation = postRequestDoc.addNewPostAssetRequest()
                .addNewPostAssetOperations();

        final Equipment equipment = operation.addNewEquipment();
        equipment.setEquipmentType(EquipmentType.AUTO_CARRIER);

        // Origin - by postal code
        final CityAndState origin = equipment.addNewOrigin().addNewCityAndState();
        origin.setCity(originCity);
        origin.setStateProvince(originState);

        // Destination - by city/state/lat/lon
        final NamedLatLon destination = equipment.addNewDestination().addNewPlace().addNewNamedCoordinates();
        destination.setCity(destinationCity);
        destination.setStateProvince(destinationState);
        destination.setLatitude(destinationLatitude);
        destination.setLongitude(destinationLongitude);

        // Length and weight
        final Dimensions d = operation.addNewDimensions();
        d.setLengthFeet(45 + random.nextInt(5));
        d.setWeightPounds(45000 + random.nextInt(5000));

        if (setAlarm) {
            final AlarmSearchCriteria criteria = operation.addNewAlarm();
            final Mileage originRadius = criteria.addNewOriginRadius();
            originRadius.setMiles(50);
            originRadius.setMethod(MileageType.ROAD);
            final Mileage destinationRadius = criteria.addNewDestinationRadius();
            destinationRadius.setMiles(50);
            destinationRadius.setMethod(MileageType.ROAD);
        }

        // Validate the request document before executing the operation
        validate(postRequestDoc);

        // Post
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final PostAssetResponseDocument responseDoc = stub.postAsset(postRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final PostAssetResult result = responseDoc.getPostAssetResponse().getPostAssetResultsArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetPostAssetSuccessData()) {
            throw new RemoteException("Post Asset Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }
    }
}
