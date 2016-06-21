package com.transcore.connexion.sample.dob;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import org.apache.axis2.AxisFault;

import cltool4j.GlobalConfigProperties;

import com.tcore.tcoreCarrierWatchTypes.LaneType;
import com.tcore.tcoreDobTypes.Contact;
import com.tcore.tcoreDobTypes.Document;
import com.tcore.tcoreDobTypes.Trailer;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince.Enum;
import com.tcore.tcoreTypes.Warning;
import com.tcore.tfmiFreightMatching.LookupDobCarriersOperation;
import com.tcore.tfmiFreightMatching.LookupDobCarriersRequestDocument;
import com.tcore.tfmiFreightMatching.LookupDobCarriersResponseDocument;
import com.tcore.tfmiFreightMatching.LookupDobCarriersResult;
import com.tcore.tfmiFreightMatching.LookupDobCarriersSuccessData;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;
import com.transcore.connexion.sample.BaseSampleClient;
import com.transcore.connexion.sample.ErrorHandling;

/**
 * This application demonstrates a DAT on-boarding carrier lookup by carrier id.
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 */
public class LookupCarriers extends BaseSampleClient {

    @Override
    protected void run() throws Exception {
        try {
            // Login
            final SessionToken sessionToken = loginDob();
            lookupDOTCarrier(sessionToken);
        } catch (final AxisFault fault) {
            System.err.println(fault.getMessage() + " : " + fault.getDetail().getText());
            fault.printStackTrace();
        }
    }

    protected void lookupDOTCarrier(final SessionToken sessionToken, final String carrierId,
            final String subEndPointUrl) throws RemoteException {
        final LookupDobCarriersRequestDocument lookupRequestDoc = LookupDobCarriersRequestDocument.Factory
                .newInstance();

        System.out.println("LookupDOTCarrier - carrierId: " + carrierId);
        System.out.println("LookupDOTCarrier - secureEndpointUrl: " + secureEndpointUrl);

        final LookupDobCarriersOperation operation = lookupRequestDoc.addNewLookupDobCarriersRequest()
                .addNewLookupDobCarriersOperations();
        operation.setCarrierId(carrierId);
        operation.setDobVersion(2); // Set the DOB version your building to. Default is one, see XSD for most
                                    // recent version.
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(
                (subEndPointUrl == null) ? secureEndpointUrl : subEndPointUrl);
        final LookupDobCarriersResponseDocument responseDoc = stub.lookupDobCarriers(lookupRequestDoc, null,
                null, sessionHeaderDocument(sessionToken));
        final LookupDobCarriersResult result = responseDoc.getLookupDobCarriersResponse()
                .getLookupDobCarriersResults();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupDobCarriersSuccessData()) {
            System.out.println("Error message: " + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        } else {
            printLookupCarriersResult(result.getLookupDobCarriersSuccessData());
        }
    }

    protected void lookupDOTCarrier(final SessionToken sessionToken) throws RemoteException {
        lookupDOTCarrier(sessionToken,
                GlobalConfigProperties.singleton().getProperty("dobCarrierExampleCarrierId"), null);
    }

    protected void printLookupCarriersResult(final LookupDobCarriersSuccessData successData) {
        if (successData != null) {
            if (successData.getDobCarrier() != null) {
                if (successData.getDobCarrier().getCarrierInfo() != null) {
                    if (successData.getDobCarrier().getCarrierInfo().getDbaName() != null) {
                        System.out.println("DBA name:"
                                + successData.getDobCarrier().getCarrierInfo().getDbaName());
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getDocket() != null) {
                        System.out.println("Docket name:"
                                + successData.getDobCarrier().getCarrierInfo().getDocket().getPrefix()
                                        .toString()
                                + successData.getDobCarrier().getCarrierInfo().getDocket().getNumber());
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getDotNumber() > 0) {
                        System.out.println("Dot Number:"
                                + successData.getDobCarrier().getCarrierInfo().getDotNumber());
                    }
                    System.out.println("Duns:" + successData.getDobCarrier().getCarrierInfo().getDuns());

                    if (successData.getDobCarrier().getCarrierInfo().getIntrastate() != null) {
                        System.out.println("Intrastate:"
                                + successData.getDobCarrier().getCarrierInfo().getIntrastate()
                                        .getIntrastateState().toString()
                                + successData.getDobCarrier().getCarrierInfo().getIntrastate()
                                        .getIntrastateCode());
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getLastUpdate() != null) {
                        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String formattedDate = formatter.format(successData.getDobCarrier()
                                .getCarrierInfo().getLastUpdate().getTime());
                        System.out.println("Carrier Info Last Update:" + formattedDate);
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getLegalName() != null) {
                        System.out.println("Legal Name:"
                                + successData.getDobCarrier().getCarrierInfo().getLegalName());
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getMailingAddress() != null) {
                        System.out.println("Mailing address...");
                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getCity() != null) {
                            System.out.println("  City:"
                                    + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                            .getCity());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getCountryCode() != null) {
                            System.out.println("  Country Code:"
                                    + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                            .getCountryCode().toString());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getFax() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getFax()
                                    .getCountryCode() != null) {
                                System.out.println("  Fax Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getFax().getCountryCode());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getFax()
                                    .getNumber() != null) {
                                System.out.println("  Fax Number:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getFax().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getFax()
                                    .getExtension() != null) {
                                System.out.println("  Fax Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getFax().getExtension());
                            }
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getPhone() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getPhone()
                                    .getCountryCode() != null) {
                                System.out.println("  Phone Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getPhone().getCountryCode().toString());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getPhone()
                                    .getNumber() != null) {
                                System.out.println("  Phone Number:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getPhone().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getPhone()
                                    .getExtension() != null) {
                                System.out.println("  Phone Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getPhone().getExtension());
                            }
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getPostalCode() != null) {
                            System.out.println("  Postal Code:"
                                    + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                            .getPostalCode());
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                .getStateProvince() != null) {
                            System.out.println("  State/Province:"
                                    + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                            .getStateProvince().toString());
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress().getStreet() != null) {
                            System.out.println("  Street:"
                                    + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                            .getStreet());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                .getTollFreePhone() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                    .getTollFreePhone().getCountryCode() != null) {
                                System.out.println("  Toll Free Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getTollFreePhone().getCountryCode().toString());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                    .getTollFreePhone().getNumber() != null) {
                                System.out.println("  Toll Free Number :"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getTollFreePhone().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                    .getTollFreePhone().getExtension() != null) {
                                System.out.println("  Toll Free Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getMailingAddress()
                                                .getTollFreePhone().getExtension());
                            }
                        }

                    }
                    if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress() != null) {
                        System.out.println("Physical address...");
                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getCity() != null) {
                            System.out.println("  City:"
                                    + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                            .getCity());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                .getCountryCode() != null) {
                            System.out.println("  Country Code:"
                                    + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                            .getCountryCode().toString());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getFax() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getFax()
                                    .getCountryCode() != null) {
                                System.out.println("  Fax Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getFax().getCountryCode());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getFax()
                                    .getNumber() != null) {
                                System.out.println("  Fax Number:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getFax().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getFax()
                                    .getExtension() != null) {
                                System.out.println("  Fax Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getFax().getExtension());
                            }
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getPhone() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getPhone()
                                    .getCountryCode() != null) {
                                System.out.println("  Phone Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getPhone().getCountryCode().toString());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getPhone()
                                    .getNumber() != null) {
                                System.out.println("  Phone Number:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getPhone().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getPhone()
                                    .getExtension() != null) {
                                System.out.println("  Phone Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getPhone().getExtension());
                            }
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getPostalCode() != null) {
                            System.out.println("  Postal Code:"
                                    + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                            .getPostalCode());
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                .getStateProvince() != null) {
                            System.out.println("  State/Province:"
                                    + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                            .getStateProvince().toString());
                        }
                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress().getStreet() != null) {
                            System.out.println("  Street:"
                                    + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                            .getStreet());
                        }

                        if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                .getTollFreePhone() != null) {
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                    .getTollFreePhone().getCountryCode() != null) {
                                System.out.println("  Toll Free Number Country Code:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getTollFreePhone().getCountryCode().toString());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                    .getTollFreePhone().getNumber() != null) {
                                System.out.println("  Toll Free Number :"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getTollFreePhone().getNumber());
                            }
                            if (successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                    .getTollFreePhone().getExtension() != null) {
                                System.out.println("  Toll Free Number Extension:"
                                        + successData.getDobCarrier().getCarrierInfo().getPhysicalAddress()
                                                .getTollFreePhone().getExtension());
                            }
                        }
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getScacCodeArray() != null) {
                        for (final String code : successData.getDobCarrier().getCarrierInfo()
                                .getScacCodeArray()) {
                            System.out.println("Scac Code:" + code);
                        }
                    }
                    if (successData.getDobCarrier().getCarrierInfo().getWebsite() != null) {
                        System.out.println("Website:"
                                + successData.getDobCarrier().getCarrierInfo().getWebsite());
                    }
                }

                if (successData.getDobCarrier().getContactInfo().getContactsArray() != null) {
                    for (final Contact c : successData.getDobCarrier().getContactInfo().getContactsArray()) {
                        System.out.println("Contact...");

                        if (c.getEmail() != null) {
                            System.out.println("  Email:" + c.getEmail());
                        }
                        if (c.getFax() != null) {
                            System.out.println("  Fax:" + c.getFax());
                        }
                        System.out.println("  Is primary contact:" + c.getIsPrimaryContact());
                        if (c.getName() != null) {
                            System.out.println("  Name:" + c.getName());
                        }
                        if (c.getPhone() != null) {
                            System.out.println("  Phone:" + c.getPhone());
                        }
                        if (c.getRole() != null) {
                            System.out.println("  Role:" + c.getRole());
                        }
                        if (c.getTitle() != null) {
                            System.out.println("  Title:" + c.getTitle());
                        }
                    }

                }

                if (successData.getDobCarrier().getContactInfo().getLastUpdate() != null) {
                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String formattedDate = formatter.format(successData.getDobCarrier()
                            .getContactInfo().getLastUpdate().getTime());
                    System.out.println("Contact Info Last Update:" + formattedDate);
                }

                if (successData.getDobCarrier().getDocumentsArray() != null) {
                    for (final Document d : successData.getDobCarrier().getDocumentsArray()) {
                        System.out.println("Document...");
                        if (d.getCreated() != null) {
                            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            final String formattedDate = formatter.format(d.getCreated().getTime());
                            System.out.println("  Created Date:" + formattedDate);
                        }
                        if (d.getDescription() != null) {
                            System.out.println("  Description:" + d.getDescription());
                        }
                        if (d.getDocumentName() != null) {
                            System.out.println("  Doc Name:" + d.getDocumentName());
                        }
                        if (d.getDocumentType() != null) {
                            System.out.println("  Type:" + d.getDocumentType());
                        }
                        if (d.getFileExtension() != null) {
                            System.out.println("  File Ext:" + d.getFileExtension());
                        }
                        if (d.getFileName() != null) {
                            System.out.println("  File Name:" + d.getFileName());
                        }
                        if (d.getUrl() != null) {
                            System.out.println("  Url:" + d.getUrl());
                        }
                    }
                }

                if (successData.getDobCarrier().getEquipment() != null) {
                    System.out.println("Equipment...");
                    System.out.println("  Has air ride:"
                            + successData.getDobCarrier().getEquipment().getHasAirRide());
                    System.out.println("  Has chains:"
                            + successData.getDobCarrier().getEquipment().getHasChains());
                    System.out.println("  Has coil racks:"
                            + successData.getDobCarrier().getEquipment().getHasCoilRacks());
                    System.out.println("  Has conestoga:"
                            + successData.getDobCarrier().getEquipment().getHasConestoga());
                    System.out.println("  Has container locks:"
                            + successData.getDobCarrier().getEquipment().getHasContainerLocks());
                    System.out.println("  Has curtains:"
                            + successData.getDobCarrier().getEquipment().getHasCurtains());
                    System.out.println("  Has etrac:"
                            + successData.getDobCarrier().getEquipment().getHasEtrac());
                    System.out.println("  Has garment racks:"
                            + successData.getDobCarrier().getEquipment().getHasGarmentRacks());
                    System.out.println("  Has hot shot:"
                            + successData.getDobCarrier().getEquipment().getHasHotshot());
                    System.out.println("  Has insulated:"
                            + successData.getDobCarrier().getEquipment().getHasInsulated());
                    System.out.println("  Has pad wrap:"
                            + successData.getDobCarrier().getEquipment().getHasPadWrap());
                    System.out.println("  Has straps:"
                            + successData.getDobCarrier().getEquipment().getHasStraps());
                    System.out.println("  Has tarps:"
                            + successData.getDobCarrier().getEquipment().getHasTarps());
                    System.out.println("  Has vented:"
                            + successData.getDobCarrier().getEquipment().getHasVented());

                    if (successData.getDobCarrier().getEquipment().getLastUpdate() != null) {
                        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String formattedDate = formatter.format(successData.getDobCarrier()
                                .getEquipment().getLastUpdate().getTime());
                        System.out.println("  Equipment Last Update Date:" + formattedDate);
                    }

                    System.out.println("  Number of Company Drivers:"
                            + successData.getDobCarrier().getEquipment().getNumberOfCompanyDrivers());
                    System.out.println("  Number of Owner Operators:"
                            + successData.getDobCarrier().getEquipment().getNumberOfOwnerOperators());
                    System.out.println("  Number of Power Units:"
                            + successData.getDobCarrier().getEquipment().getNumberOfPowerUnits());
                    System.out.println("  Number of Teams:"
                            + successData.getDobCarrier().getEquipment().getNumberOfTeams());

                    if (successData.getDobCarrier().getEquipment().getOnBoardCommunications() != null) {
                        System.out.println("  On board communications:"
                                + successData.getDobCarrier().getEquipment().getOnBoardCommunications()
                                        .toString());
                    }
                    if (successData.getDobCarrier().getEquipment().getTrailersArray() != null) {
                        for (final Trailer t : successData.getDobCarrier().getEquipment().getTrailersArray()) {
                            System.out.println("  Number of trailers:" + t.getNumberOf());
                            if (t.getTrailerType() != null) {
                                System.out.println("  Trailer type:" + t.getTrailerType().toString());
                            }
                        }
                    }
                }

                if (successData.getDobCarrier().getGeographicCoverage() != null) {
                    if (successData.getDobCarrier().getGeographicCoverage().getLanesArray() != null) {
                        for (final LaneType lt : successData.getDobCarrier().getGeographicCoverage()
                                .getLanesArray()) {
                            System.out.println("Lane Type...");

                            if (lt.getOrigin().getStateProvince() != null) {
                                System.out.println(" Origin state:"
                                        + lt.getOrigin().getStateProvince().toString());
                            }
                            if (lt.getOrigin().getMetroArea() != null) {
                                System.out.println(" Origin metro area:" + lt.getOrigin().getMetroArea());
                            }
                            if (lt.getDestination().getStateProvince() != null) {
                                System.out.println(" Destination state:"
                                        + lt.getDestination().getStateProvince());
                            }
                            if (lt.getDestination().getMetroArea() != null) {
                                System.out.println(" Destination metro area:"
                                        + lt.getDestination().getMetroArea());
                            }
                        }
                    }
                    if (successData.getDobCarrier().getGeographicCoverage().getLastUpdate() != null) {
                        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String formattedDate = formatter.format(successData.getDobCarrier()
                                .getGeographicCoverage().getLastUpdate().getTime());
                        System.out.println("Last Geographic Coverage Update Date:" + formattedDate);
                    }
                    if (successData.getDobCarrier().getGeographicCoverage().getOperatingStateProvinceArray() != null) {
                        for (final Enum en : successData.getDobCarrier().getGeographicCoverage()
                                .getOperatingStateProvinceArray()) {
                            System.out.println("Geographic Coverage operation state province:"
                                    + en.toString());
                        }
                    }
                    System.out.println("Geographic Coverage service in mexico:"
                            + successData.getDobCarrier().getGeographicCoverage().getServiceInMexico());
                }

                // if(successData.getDobCarrier().getInsuranceAgentInfo() != null){
                // successData.getDobCarrier().getInsuranceAgentInfo().getAgentsArray()
                // successData.getDobCarrier().getInsuranceAgentInfo().getLastUpdate()
                // }
                //
                // if(successData.getDobCarrier().getPaymentInfo() != null){
                // successData.getDobCarrier().getPaymentInfo().getInterestInQuickPay()
                // successData.getDobCarrier().getPaymentInfo().getIsFactoringCompany()
                // successData.getDobCarrier().getPaymentInfo().getLastUpdate()
                // successData.getDobCarrier().getPaymentInfo().getReferencesArray()
                // successData.getDobCarrier().getPaymentInfo().getRemitToAddress()
                // successData.getDobCarrier().getPaymentInfo().getRemitToName()
                // }
                //
                // if(successData.getDobCarrier().getServices() != null){
                // successData.getDobCarrier().getServices().getAirportsArray()
                // successData.getDobCarrier().getServices().getBrokerDocket()
                // successData.getDobCarrier().getServices().getCompanyType()
                // successData.getDobCarrier().getServices().getCsaCode()
                // successData.getDobCarrier().getServices().getDoesBrokerage()
                // successData.getDobCarrier().getServices().getDoesMultiStops()
                // successData.getDobCarrier().getServices().getDoesPalletExchange()
                // successData.getDobCarrier().getServices().getDoesParcels()
                // successData.getDobCarrier().getServices().getDoesTrailerInterchange()
                // successData.getDobCarrier().getServices().getFastCode()
                // successData.getDobCarrier().getServices().getHandlesAirFreightCartage()
                // successData.getDobCarrier().getServices().getHandlesEdi()
                // successData.getDobCarrier().getServices().getHandlesExpeditedGround()
                // successData.getDobCarrier().getServices().getHandlesOversizeLoads()
                // successData.getDobCarrier().getServices().getHandlesPortDryage()
                // successData.getDobCarrier().getServices().getHandlesRailDryage()
                // successData.getDobCarrier().getServices().getHasCarbCompliantTrucks()
                // successData.getDobCarrier().getServices().getHasLTLCapabilities()
                // successData.getDobCarrier().getServices().getHasSpottedTrailers()
                // successData.getDobCarrier().getServices().getIacCertification()
                // }
                //
                // if(successData.getDobCarrier().getTaxInfo() != null){
                // successData.getDobCarrier().getTaxInfo().getLastUpdate()
                // successData.getDobCarrier().getTaxInfo().getW8Info()
                // successData.getDobCarrier().getTaxInfo().getW9Info()
                // }
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
