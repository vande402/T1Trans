package com.transcore.connexion.sample;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.tcore.tcoreTypes.AssetType;
import com.tcore.tcoreTypes.EquipmentClass;
import com.tcore.tcoreTypes.MileageType;
import com.tcore.tcoreTypes.ServiceError;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tcoreTypes.Warning;
import com.tcore.tfmiFreightMatching.CreateSearchOperation;
import com.tcore.tfmiFreightMatching.CreateSearchRequestDocument;
import com.tcore.tfmiFreightMatching.CreateSearchResponse;
import com.tcore.tfmiFreightMatching.CreateSearchResponseDocument;
import com.tcore.tfmiFreightMatching.DeleteAssetOperation;
import com.tcore.tfmiFreightMatching.DeleteAssetRequestDocument;
import com.tcore.tfmiFreightMatching.DeleteAssetResponseDocument;
import com.tcore.tfmiFreightMatching.DeleteAssetResult;
import com.tcore.tfmiFreightMatching.LoginOperation;
import com.tcore.tfmiFreightMatching.LoginRequestDocument;
import com.tcore.tfmiFreightMatching.LoginResponseDocument;
import com.tcore.tfmiFreightMatching.LoginResult;
import com.tcore.tfmiFreightMatching.MatchingAsset;
import com.tcore.tfmiFreightMatching.PostAssetResult;
import com.tcore.tfmiFreightMatching.SearchCriteria;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;

/**
 * Demonstrates several error scenarios and the exceptions and messages generated.
 * 
 * The errors fall into x classes:
 * 
 * <ul>
 * <li>Transport-layer errors - e.g., inability to connect to the server. If unable to connect, the Axis2
 * framework will retry 3 times, and then throw an {@link AsixFault}.</li>
 * 
 * <li>XML validation errors. All sample application code demonstrates validating the request document prior
 * to executing the call. However, if the client does _not_ perform that validation and sends an invalid
 * document to the server, the server will reject the document and return a SOAP fault. The framework will
 * again throw an {@link AxisFault}.</li>
 * 
 * <li>Errors returned by the server. Unsuccessful requests, such as login with invalid loginId/password or
 * posting or searching with invalid parameters, are returned using the {@link ServiceError} mechanism
 * described in the Connexion Developers Overview document.</li>
 * 
 * <li>Server-side failures. These are hopefully quite rare, but failures in TransCore's servers will also be
 * returned as {@link AxisFault}s.</li>
 * 
 * </ul>
 * 
 * This application demonstrates how to catch each class of error and obtain the description thereof. The
 * client application should log or display these error messages for troubleshooting purposes. In the event of
 * unexpected errors, please include the full detail of any error messages in your communications with
 * TransCore Technical Support.
 * 
 */
public class ErrorHandling extends BaseSampleClient {

    @Override
    protected void run() throws Exception {

        //
        // Attempt to connect to a nonexistent endpoint. This simulates a network failure, a firewall blocking
        // the outbound connection, or a TransCore server failure. The underlying call will attempt to connect
        // 3 times and then throw an AxisFault
        //

        System.out.println("=== Attempting to login using a nonexistent endpoint ===");

        try {
            final LoginRequestDocument loginRequestDoc = LoginRequestDocument.Factory.newInstance();
            final LoginOperation loginOperation = loginRequestDoc.addNewLoginRequest().addNewLoginOperation();
            loginOperation.setLoginId("loginId");
            loginOperation.setPassword("password");
            loginOperation.setThirdPartyId("ErrorHandlingSample");
            final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(
                    "http://208.64.206.159:9281/TfmiRequest");
            stub.login(loginRequestDoc, null, null, null);
        } catch (final AxisFault expected) {
            System.out.println("Fault message: " + expected.getMessage());
            System.out.println();
        }

        //
        // Try to login to the correct endpoint, specifying a loginId longer than allowed by the XML Schema.
        // The server will return a schema validation error. See below (or in other sample applications) for
        // code demonstrating how to perform validation prior to sending the HTTP request to TransCore.
        //

        System.out.println("=== Attempting to login with an invalid XML request document ===");

        try {
            final LoginRequestDocument loginRequestDoc = LoginRequestDocument.Factory.newInstance();
            final LoginOperation loginOperation = loginRequestDoc.addNewLoginRequest().addNewLoginOperation();
            loginOperation.setLoginId("tooLongCustomerNameForLoginId");
            loginOperation.setPassword("password");
            loginOperation.setThirdPartyId("ErrorHandlingSample");

            final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
            stub.login(loginRequestDoc, null, null, null);

        } catch (final AxisFault expected) {
            System.out.println("Fault message: " + expected.getMessage());
            System.out.println("Fault detail: "
                    + expected.getFaultDetailElement().getFirstElement().getText());
            System.out.println();
        }

        //
        // Try to login using invalid loginId/password credentials. This request will return an error from
        // TransCore's servers, which we can verify by checking for the SuccessData element.
        //

        System.out.println("=== Logging in with invalid loginId / password ===");

        final LoginRequestDocument loginRequestDoc = LoginRequestDocument.Factory.newInstance();
        final LoginOperation loginOperation = loginRequestDoc.addNewLoginRequest().addNewLoginOperation();
        loginOperation.setLoginId("badLogin");
        loginOperation.setPassword("password");
        loginOperation.setThirdPartyId("ErrorHandlingSample");

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LoginResponseDocument loginResponseDoc = stub.login(loginRequestDoc, null, null, null);
        final LoginResult loginResult = loginResponseDoc.getLoginResponse().getLoginResult();

        if (!loginResult.isSetLoginSuccessData()) {
            // Handle login failure here
            System.out.println("Error code: " + loginResult.getServiceError().getCode());
            System.out.println("Message: " + loginResult.getServiceError().getMessage());
            System.out.println("Detailed Message: " + loginResult.getServiceError().getDetailedMessage());
            System.out.println();
        } else {
            System.out.println("Expected ServiceError");
        }

        //
        // Now try another request which will return an error from TransCore's servers. Searching from open ->
        // open is not allowed.
        //

        System.out.println("=== Executing an invalid search ===");

        // Login with a valid loginId and password
        final SessionToken sessionToken = loginUser1();

        try {
            final CreateSearchResponse searchResponse = searchOpen2Open(sessionToken);
            if (!searchResponse.getCreateSearchResult().isSetCreateSearchSuccessData()) {
                System.out.println("Error code: "
                        + searchResponse.getCreateSearchResult().getServiceError().getCode());
                System.out.println("Message: "
                        + searchResponse.getCreateSearchResult().getServiceError().getMessage());
                System.out.println("Detailed Message: "
                        + searchResponse.getCreateSearchResult().getServiceError().getDetailedMessage());
            }
            System.out.println();
        } catch (final AxisFault e) {
            System.err.println("Unexpected AxisFault: " + e.getMessage());
        }

        //
        // Post an asset, delete it, and attempt to delete it again. A Warning will be returned
        //

        System.out.println("=== Simulating a warning condition (re-deleting an already-deleted asset) ===");

        try {
            final PostAssetResult postResult = postShipment(sessionToken, "Salina", StateProvince.KS, "Nome",
                    StateProvince.AK, 64.5f, -165.404f);
            final DeleteAssetRequestDocument deleteRequestDoc = DeleteAssetRequestDocument.Factory
                    .newInstance();
            final DeleteAssetOperation operation = deleteRequestDoc.addNewDeleteAssetRequest()
                    .addNewDeleteAssetOperation();
            operation.addNewDeleteAssetsByAssetIds().addAssetIds(
                    postResult.getPostAssetSuccessData().getAssetId());

            // Validate the request document before executing the operation
            validate(deleteRequestDoc);

            // Delete
            stub.deleteAsset(deleteRequestDoc, null, null, sessionHeaderDocument(sessionToken));

            // Now try to re-delete
            final DeleteAssetResponseDocument delete2ResponseDoc = stub.deleteAsset(deleteRequestDoc, null,
                    null, sessionHeaderDocument(sessionToken));

            final DeleteAssetResult result = delete2ResponseDoc.getDeleteAssetResponse()
                    .getDeleteAssetResult();
            System.out.println("Warning (expected): "
                    + result.getDeleteAssetSuccessData().getWarningsArray(0).getMessage());
        } catch (final AxisFault e) {
            System.err.println("Unexpected AxisFault: " + e.getMessage());
        }

        //
        // Execute a valid search, with all appropriate error handling
        //
        try {
            final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                    .newInstance();
            final CreateSearchOperation searchOperation = searchRequestDoc.addNewCreateSearchRequest()
                    .addNewCreateSearchOperation();

            final SearchCriteria criteria = searchOperation.addNewCriteria();

            criteria.setAssetType(AssetType.EQUIPMENT);

            criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);
            criteria.setAgeLimitMinutes(2);

            // Origin = Atlanta, GA, 100 mile radius
            criteria.addNewOrigin().addNewRadius().addNewPlace().addNewCityAndState().setCity("Atlanta");
            criteria.getOrigin().getRadius().getPlace().getCityAndState().setStateProvince(StateProvince.GA);
            criteria.getOrigin().getRadius().addNewRadius().setMiles(100);
            criteria.getOrigin().getRadius().getRadius().setMethod(MileageType.ROAD);

            // Destination = Open
            criteria.addNewDestination().addNewOpen();

            // Validate the request document before executing the search
            validate(searchRequestDoc);

            // Execute the search
            final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                    sessionHeaderDocument(sessionToken));
            final CreateSearchResponse searchResponse = responseDoc.getCreateSearchResponse();

            // Ensure the response contains a SuccessData element
            if (searchResponse.getCreateSearchResult().isSetCreateSearchSuccessData()) {

                if (searchResponse.getCreateSearchResult().getCreateSearchSuccessData().sizeOfWarningsArray() > 0) {
                    // Print out warnings
                    for (final Warning w : searchResponse.getCreateSearchResult()
                            .getCreateSearchSuccessData().getWarningsArray()) {
                        System.out.println("Warning: " + w.getCode() + " : " + w.getMessage());
                    }
                }

                // Print out summaries of each match
                for (final MatchingAsset match : searchResponse.getCreateSearchResult()
                        .getCreateSearchSuccessData().getMatchesArray()) {
                    System.out.println(summaryString(match));
                }

            } else {
                // Handle request-specific ServiceError
                System.out.println("Error code: "
                        + searchResponse.getCreateSearchResult().getServiceError().getCode());
                System.out.println("Message: "
                        + searchResponse.getCreateSearchResult().getServiceError().getMessage());
                System.out.println("Detailed Message: "
                        + searchResponse.getCreateSearchResult().getServiceError().getDetailedMessage());

            }

        } catch (final AxisFault e) {
            // Handle SOAP Fault or XML validation errors
            System.out.println("Fault message: " + e.getMessage());
        }
    }

    /**
     * Searches for equipment, specifying origin and destination as open (a request which will pass XML Schema
     * validation, but will be rejected by TransCore).
     * 
     * @throws RemoteException
     */
    protected CreateSearchResponse searchOpen2Open(final SessionToken sessionToken) throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.EQUIPMENT);

        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);
        criteria.setAgeLimitMinutes(2);

        criteria.addNewOrigin().addNewOpen();
        criteria.addNewDestination().addNewOpen();

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        return responseDoc.getCreateSearchResponse();
    }

    /**
     * Searches for equipment from Atlanta,GA -> Open.
     * 
     * @throws RemoteException
     */
    protected CreateSearchResponse searchAtlanta2Open(final SessionToken sessionToken) throws RemoteException {

        final CreateSearchRequestDocument searchRequestDoc = CreateSearchRequestDocument.Factory
                .newInstance();
        final CreateSearchOperation operation = searchRequestDoc.addNewCreateSearchRequest()
                .addNewCreateSearchOperation();

        final SearchCriteria criteria = operation.addNewCriteria();

        criteria.setAssetType(AssetType.EQUIPMENT);

        criteria.addEquipmentClasses(EquipmentClass.DRY_BULK);
        criteria.setAgeLimitMinutes(2);

        // Origin = Atlanta, GA, 100 mile radius
        criteria.addNewOrigin().addNewRadius().addNewPlace().addNewCityAndState().setCity("Atlanta");
        criteria.getOrigin().getRadius().getPlace().getCityAndState().setStateProvince(StateProvince.GA);
        criteria.getOrigin().getRadius().addNewRadius().setMiles(100);
        criteria.getOrigin().getRadius().getRadius().setMethod(MileageType.ROAD);

        // Destination = Open
        criteria.addNewDestination().addNewOpen();

        // Execute the search
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final CreateSearchResponseDocument responseDoc = stub.createSearch(searchRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        return responseDoc.getCreateSearchResponse();
    }

    public static void main(final String[] args) {
        run(args);
    }

}
