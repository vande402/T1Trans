package com.transcore.connexion.sample.dob;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import org.apache.axis2.AxisFault;

import com.tcore.tcoreDobTypes.Contract;
import com.tcore.tcoreDobTypes.Signatory;
import com.tcore.tcoreDobTypes.SignedCarrier;
import com.tcore.tcoreDobTypes.SignedContract;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.Warning;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersOperation;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersRequest;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersRequestDocument;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersResponseDocument;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersResult;
import com.tcore.tfmiFreightMatching.LookupDobSignedCarriersSuccessData;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;
import com.transcore.connexion.sample.BaseSampleClient;

public class LookupSignedCarriers extends BaseSampleClient {

    @Override
    protected void run() throws Exception {
        try {
            // Login
            final SessionToken sessionToken = loginDob();
            lookupDobSignedCarriers(sessionToken);
        } catch (final AxisFault fault) {
            // System.err.println(fault.getMessage() + " : " + fault.getDetail().getText());
            System.out.println(fault);
            fault.printStackTrace();
        }
    }

    protected void lookupDobSignedCarriers(final SessionToken sessionToken) throws RemoteException {
        final LookupDobSignedCarriersRequestDocument lookupRequestDoc = LookupDobSignedCarriersRequestDocument.Factory
                .newInstance();

        final LookupDobSignedCarriersRequest request = lookupRequestDoc
                .addNewLookupDobSignedCarriersRequest();
        final LookupDobSignedCarriersOperation operation = request.addNewLookupDobSignedCarriersOperations();
        operation.setDobVersion(2); // Set the DOB version your building to. Default is one, see XSD for most
                                    // recent version.
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(secureEndpointUrl);

        final LookupDobSignedCarriersResponseDocument responseDoc = stub.lookupDobSignedCarriers(
                lookupRequestDoc, null, null, sessionHeaderDocument(sessionToken));

        final LookupDobSignedCarriersResult result = responseDoc.getLookupDobSignedCarriersResponse()
                .getLookupDobSignedCarriersResults();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupDobSignedCarriersSuccessData()) {
            System.out.println("Error message: " + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        } else {
            printLookupSignedCarriersResult(result.getLookupDobSignedCarriersSuccessData(), sessionToken);
        }

    }

    protected void printLookupSignedCarriersResult(final LookupDobSignedCarriersSuccessData successData,
            final SessionToken sessionToken) {
        if (successData != null) {
            if (successData.getSignedCarriersArray() != null) {
                for (final SignedCarrier sc : successData.getSignedCarriersArray()) {
                    if (sc.getCarrierId() != null) {
                        System.out.println("carrier id:" + sc.getCarrierId());
                        System.out.println("secureEndpointUrl:" + secureEndpointUrl);
                        final LookupCarriers lookupCarriers = new LookupCarriers();
                        try {
                            lookupCarriers.lookupDOTCarrier(sessionToken, sc.getCarrierId(),
                                    secureEndpointUrl);
                        } catch (final Exception e) {
                            System.out.println("Exception when looking up carrier detail:" + e);
                            e.printStackTrace();
                        }
                    }
                    if (sc.getSignedContractsArray() != null) {
                        for (final SignedContract signedContract : sc.getSignedContractsArray()) {

                            if (signedContract.getContract() != null) {
                                System.out.println("Signed contract...");
                                final Contract contract = signedContract.getContract();
                                if (contract.getDescription() != null) {
                                    System.out.println("  Description:" + contract.getDescription());
                                }
                                if (contract.getDocumentStatus() != null) {
                                    System.out.println("  Document Status:"
                                            + contract.getDocumentStatus().toString());
                                }
                                if (contract.getName() != null) {
                                    System.out.println("  Name:" + contract.getName());
                                }
                                System.out.println("  Version:" + contract.getVersion());
                            }

                            if (signedContract.getSignatory() != null) {
                                System.out.println("Signatory...");
                                final Signatory sig = signedContract.getSignatory();
                                if (sig.getName() != null) {
                                    System.out.println("  Name:" + sig.getName());
                                }
                                if (sig.getTitle() != null) {
                                    System.out.println("  Title:" + sig.getTitle());
                                }
                            }

                            if (signedContract.getUrl() != null) {
                                System.out.println("  url:" + signedContract.getUrl());
                            }
                            if (signedContract.getSigned() != null) {
                                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                final String formattedDate = formatter.format(signedContract.getSigned());
                                System.out.println("Contract Signed Date:" + formattedDate);
                            }
                        }
                    }
                }
            }
            if (successData.getWarningsArray() != null) {
                for (final Warning w : successData.getWarningsArray()) {
                    System.out.println("Warning code:" + w.getCode());
                    System.out.println("Warning message:" + w.getMessage());
                }
            }
        }
    }

    public static void main(final String[] args) {
        run(args);
    }
}
