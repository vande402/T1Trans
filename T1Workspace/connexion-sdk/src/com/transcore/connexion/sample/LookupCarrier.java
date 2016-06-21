package com.transcore.connexion.sample;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.tcore.tcoreCarrierWatchTypes.CommodityType;
import com.tcore.tcoreCarrierWatchTypes.Csa2010BasicMeasurements;
import com.tcore.tcoreCarrierWatchTypes.Csa2010BasicMeasurements.Measurement;
import com.tcore.tcoreCarrierWatchTypes.Csa2010SafetyFitness;
import com.tcore.tcoreCarrierWatchTypes.DotAuthority;
import com.tcore.tcoreCarrierWatchTypes.DotInsuranceType;
import com.tcore.tcoreCarrierWatchTypes.DotProfile;
import com.tcore.tcoreCarrierWatchTypes.EndPoint;
import com.tcore.tcoreCarrierWatchTypes.ExtendedProfile;
import com.tcore.tcoreCarrierWatchTypes.ExtendedProfile.CompanyBackground;
import com.tcore.tcoreCarrierWatchTypes.ExtendedProfile.Fleet;
import com.tcore.tcoreCarrierWatchTypes.ExtendedProfile.Fleet.Truck;
import com.tcore.tcoreCarrierWatchTypes.ExtendedProfile.Services;
import com.tcore.tcoreCarrierWatchTypes.FmcsaCrashes;
import com.tcore.tcoreCarrierWatchTypes.FmcsaInspections;
import com.tcore.tcoreCarrierWatchTypes.FmcsaSafeStat;
import com.tcore.tcoreCarrierWatchTypes.FmcsaSafetyRating;
import com.tcore.tcoreCarrierWatchTypes.Header;
import com.tcore.tcoreCarrierWatchTypes.InsuranceCertificate;
import com.tcore.tcoreCarrierWatchTypes.InsuranceCertificate.CoverageLimit;
import com.tcore.tcoreCarrierWatchTypes.InsuranceCertificates;
import com.tcore.tcoreCarrierWatchTypes.InsuranceRecord;
import com.tcore.tcoreCarrierWatchTypes.LaneType;
import com.tcore.tcoreCarrierWatchTypes.Location;
import com.tcore.tcoreTypes.PhoneNumber;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tfmiFreightMatching.LookupCarrierOperation;
import com.tcore.tfmiFreightMatching.LookupCarrierRequestDocument;
import com.tcore.tfmiFreightMatching.LookupCarrierResponseDocument;
import com.tcore.tfmiFreightMatching.LookupCarrierResult;
import com.tcore.tfmiFreightMatching.LookupCarrierSuccessData;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;

/**
 * This application demonstrates a simple carrier lookup by DOT number, MC number, and TransCore UserId.
 * 
 * When examining a search result, the posting owner will be represented as a UserId, and the carrier
 * information can be referenced by that UserId.
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 */
public class LookupCarrier extends BaseSampleClient {

    /**
     * A valid DOT carrier number.
     */
    public static final int DOT_NUMBER = 1258500;

    /**
     * A valid MC number.
     */
    public static final int MC_NUMBER = 177051;

    /**
     * A valid UserID.
     */
    public static final int USER_ID = 12;

    /**
     * An invalid MC number.
     */
    public static final int INVALID_MC_NUMBER = 0;

    @Override
    public void run() throws RemoteException {
        try {
            // Login
            final SessionToken sessionToken = loginUser1();

            lookupByDOTNumber(sessionToken);
            lookupByMCNumber(sessionToken);
            lookupByUserId(sessionToken);
            lookupByInvalidMCNumber(sessionToken);
        } catch (final AxisFault fault) {
            System.err.println(fault.getMessage() + " : " + fault.getDetail().getText());
            fault.printStackTrace();
        }
    }

    /**
     * Looks up a carrier by DOT number.
     * 
     * @throws RemoteException
     */
    protected void lookupByDOTNumber(final SessionToken sessionToken) throws RemoteException {

        System.out.println("======= Lookup By DOT Number =======\n");
        final LookupCarrierRequestDocument lookupRequestDoc = LookupCarrierRequestDocument.Factory
                .newInstance();
        final LookupCarrierOperation operation = lookupRequestDoc.addNewLookupCarrierRequest()
                .addNewLookupCarrierOperation();

        operation.setDotNumber(DOT_NUMBER);

        operation.setIncludeDotAuthority(true);
        operation.setIncludeDotInsurance(true);
        operation.setIncludeDotProfile(true);
        operation.setIncludeFmcsaCrashes(true);
        operation.setIncludeFmcsaInspections(true);
        operation.setIncludeFmcsaSafeStat(true);
        operation.setIncludeFmcsaSafetyRating(true);
        operation.setIncludeCsa2010Basic(true);
        operation.setIncludeCsa2010SafetyFitness(true);
        operation.setIncludeExtendedProfile(true);
        operation.setIncludeInsuranceCertificates(true);

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupCarrierResponseDocument responseDoc = stub.lookupCarrier(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupCarrierResult result = responseDoc.getLookupCarrierResponse()
                .getLookupCarrierResultArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupCarrierSuccessData()) {
            throw new RemoteException("Lookup Carrier Request Failed: "
                    + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        }

        printLookupCarrierResult(result.getLookupCarrierSuccessData());
    }

    /**
     * Looks up a carrier by MC number.
     * 
     * @throws RemoteException
     */
    protected void lookupByMCNumber(final SessionToken sessionToken) throws RemoteException {

        System.out.println("\n======= Lookup By MC Number =======\n");
        final LookupCarrierRequestDocument lookupRequestDoc = LookupCarrierRequestDocument.Factory
                .newInstance();
        final LookupCarrierOperation operation = lookupRequestDoc.addNewLookupCarrierRequest()
                .addNewLookupCarrierOperation();

        operation.setMcNumber(MC_NUMBER);

        operation.setIncludeDotAuthority(true);
        operation.setIncludeDotInsurance(true);
        operation.setIncludeDotProfile(true);
        operation.setIncludeFmcsaCrashes(true);
        operation.setIncludeFmcsaInspections(true);
        operation.setIncludeFmcsaSafeStat(true);
        operation.setIncludeFmcsaSafetyRating(true);
        operation.setIncludeCsa2010Basic(true);
        operation.setIncludeCsa2010SafetyFitness(true);
        operation.setIncludeExtendedProfile(true);
        operation.setIncludeInsuranceCertificates(true);

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupCarrierResponseDocument responseDoc = stub.lookupCarrier(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupCarrierResult result = responseDoc.getLookupCarrierResponse()
                .getLookupCarrierResultArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupCarrierSuccessData()) {
            throw new RemoteException("Lookup Carrier Request Failed: "
                    + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        }

        printLookupCarrierResult(result.getLookupCarrierSuccessData());
    }

    /**
     * Looks up a carrier by UserId (primarily useful for looking up the owner of a search match)
     * 
     * @throws RemoteException
     */
    protected void lookupByUserId(final SessionToken sessionToken) throws RemoteException {

        System.out.println("\n======= Lookup By UserId =======\n");
        final LookupCarrierRequestDocument lookupRequestDoc = LookupCarrierRequestDocument.Factory
                .newInstance();
        final LookupCarrierOperation operation = lookupRequestDoc.addNewLookupCarrierRequest()
                .addNewLookupCarrierOperation();

        operation.setUserId(USER_ID);

        operation.setIncludeDotAuthority(true);
        operation.setIncludeDotInsurance(true);
        operation.setIncludeDotProfile(true);
        operation.setIncludeFmcsaCrashes(true);
        operation.setIncludeFmcsaInspections(true);
        operation.setIncludeFmcsaSafeStat(true);
        operation.setIncludeFmcsaSafetyRating(true);
        operation.setIncludeCsa2010Basic(true);
        operation.setIncludeCsa2010SafetyFitness(true);
        operation.setIncludeExtendedProfile(true);
        operation.setIncludeInsuranceCertificates(true);

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupCarrierResponseDocument responseDoc = stub.lookupCarrier(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupCarrierResult result = responseDoc.getLookupCarrierResponse()
                .getLookupCarrierResultArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupCarrierSuccessData()) {
            throw new RemoteException("Lookup Carrier Request Failed: "
                    + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        }

        printLookupCarrierResult(result.getLookupCarrierSuccessData());
    }

    /**
     * Looks up an invalid MC Number and demonstrates the error response returned.
     * 
     * @throws RemoveException
     */
    protected void lookupByInvalidMCNumber(final SessionToken sessionToken) throws RemoteException {

        System.out.println("\n======= Lookup By Invalid MC Number =======\n *** Expecting an error ***\n");

        final LookupCarrierRequestDocument lookupRequestDoc = LookupCarrierRequestDocument.Factory
                .newInstance();
        final LookupCarrierOperation operation = lookupRequestDoc.addNewLookupCarrierRequest()
                .addNewLookupCarrierOperation();

        operation.setMcNumber(INVALID_MC_NUMBER);

        operation.setIncludeDotAuthority(true);
        operation.setIncludeDotInsurance(true);
        operation.setIncludeDotProfile(true);
        operation.setIncludeFmcsaCrashes(true);
        operation.setIncludeFmcsaInspections(true);
        operation.setIncludeFmcsaSafeStat(true);
        operation.setIncludeFmcsaSafetyRating(true);
        operation.setIncludeCsa2010Basic(true);
        operation.setIncludeCsa2010SafetyFitness(true);
        operation.setIncludeExtendedProfile(true);
        operation.setIncludeInsuranceCertificates(true);

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupCarrierResponseDocument responseDoc = stub.lookupCarrier(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupCarrierResult result = responseDoc.getLookupCarrierResponse()
                .getLookupCarrierResultArray(0);

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupCarrierSuccessData()) {
            System.out.println("Error message: " + result.getServiceError().getMessage() + " : "
                    + result.getServiceError().getDetailedMessage());
        } else {
            System.err.println("Expected an error, but received a successful response");
        }
    }

    /**
     * Display the results of a CarrierWatch lookup.
     * 
     * @param successData
     *            The CarrierWatch lookup response to display.
     */
    protected void printLookupCarrierResult(final LookupCarrierSuccessData successData) {

        // Header
        if (successData.isSetHeader()) {
            final Header header = successData.getHeader();
            System.out.println("=== Header ===");

            System.out.println("  Legal Name: " + header.getLegalName());
            if (header.isSetDbaName()) {
                System.out.println("  DBA Name: " + header.getDbaName());
            }
            if (header.isSetDotNumber()) {
                System.out.println("  DOT Number: " + header.getDotNumber());
            }
            if (header.isSetDocket()) {
                System.out.println("  Docket : " + header.getDocket().getPrefix().toString() + " "
                        + header.getDocket().getNumber());
            }
            if (header.isSetIntrastate()) {
                System.out.println("  Intrastate: " + header.getIntrastate());
            }
            if (header.isSetDuns()) {
                System.out.println("  DUNS: " + header.getDuns());
            }
            for (final String scac : header.getScacArray()) {
                System.out.println("  SCAC: " + scac);
            }
            System.out.println("  Physical Location:");
            System.out.println(locationString(header.getPhysicalLocation(), "    "));
            if (header.isSetMailingLocation()) {
                System.out.println("  Mailing Location:");
                System.out.println(locationString(header.getMailingLocation(), "    "));
            }

            System.out.println();
        }

        // DOT Profile
        if (successData.isSetDotProfile()) {
            final DotProfile dotProfile = successData.getDotProfile();
            System.out.println("=== DOT Profile ===");
            System.out.println("  Last Update: "
                    + dotProfile.getLastUpdateOfDotProfile().getTime().toString());

            System.out.println("  Active: " + dotProfile.getIsActive());
            if (dotProfile.isSetEntityType()) {
                System.out.println("  Entity Type: " + dotProfile.getEntityType());
            }
            if (dotProfile.isSetOperationType()) {
                System.out.println("  Operation Type: " + dotProfile.getOperationType());
            }
            if (dotProfile.isSetOutOfInterstateServiceDate()) {
                System.out.println("  Out of interstate service: "
                        + dotProfile.getOutOfInterstateServiceDate().getTime().toString());
            }
            if (dotProfile.isSetPowerUnits()) {
                System.out.println("  Power Units: " + dotProfile.getPowerUnits());
            }
            if (dotProfile.isSetDrivers()) {
                System.out.println("  Drivers: " + dotProfile.getDrivers());
            }
            if (dotProfile.isSetMcs150Mileage()) {
                System.out.println("  MCS Mileage: " + dotProfile.getMcs150Mileage().getMileageYear() + ' '
                        + dotProfile.getMcs150Mileage().getFormDate().getTime().toString() + ' '
                        + dotProfile.getMcs150Mileage().getMileage());
            }
            for (final CommodityType.Enum commodity : dotProfile.getCommoditiesArray()) {
                System.out.println("  Commodity: " + commodity.toString());
            }
            for (final String specialCommodity : dotProfile.getSpecialCommoditiesArray()) {
                System.out.println("  Special Commodity: " + specialCommodity);
            }
        }

        // DOT Authority
        if (successData.isSetDotAuthority()) {
            final DotAuthority dotAuthority = successData.getDotAuthority();
            System.out.println("=== DOT Authority ===");
            System.out.println("  Last Update: "
                    + dotAuthority.getLastUpdateOfDotAuthority().getTime().toString());

            if (dotAuthority.isSetCommonAuthority()) {
                System.out.println("  Common Authority: " + dotAuthority.getCommonAuthority().toString());
            }
            if (dotAuthority.isSetPendingCommonAuthority()) {
                System.out.println("  Pending Common Authority: " + dotAuthority.getPendingCommonAuthority());
            }
            if (dotAuthority.isSetRevokedCommonAuthority()) {
                System.out.println("  Revoked Common Authority: " + dotAuthority.getRevokedCommonAuthority());
            }
            if (dotAuthority.isSetContractAuthority()) {
                System.out.println("  Contract Authority: " + dotAuthority.getContractAuthority().toString());
            }
            if (dotAuthority.isSetPendingContractAuthority()) {
                System.out.println("  Pending Contract Authority: "
                        + dotAuthority.getPendingContractAuthority());
            }
            if (dotAuthority.isSetRevokedContractAuthority()) {
                System.out.println("  Revoked Contract Authority: "
                        + dotAuthority.getRevokedContractAuthority());
            }
            if (dotAuthority.isSetBrokerAuthority()) {
                System.out.println("  Broker Authority: " + dotAuthority.getBrokerAuthority().toString());
            }
            if (dotAuthority.isSetPendingBrokerAuthority()) {
                System.out.println("  Pending Broker Authority: " + dotAuthority.getPendingBrokerAuthority());
            }
            if (dotAuthority.isSetRevokedBrokerAuthority()) {
                System.out.println("  Revoked Broker Authority: " + dotAuthority.getRevokedBrokerAuthority());
            }
            if (dotAuthority.isSetCarriesFreight()) {
                System.out.println("  Carries Freight: " + dotAuthority.getCarriesFreight());
            }
            if (dotAuthority.isSetCarriesPassengers()) {
                System.out.println("  Carries Passengers: " + dotAuthority.getCarriesPassengers());
            }
            if (dotAuthority.isSetCarriesHhg()) {
                System.out.println("  Carries HHG: " + dotAuthority.getCarriesHhg());
            }
            if (dotAuthority.isSetBipdRequired()) {
                System.out.println("  BIPD Required: " + dotAuthority.getBipdRequired());
            }
            if (dotAuthority.isSetCargoRequired()) {
                System.out.println("  Cargo Required: " + dotAuthority.getCargoRequired());
            }
            if (dotAuthority.isSetBondSuretyRequired()) {
                System.out.println("  Bond Surety Required: " + dotAuthority.getBondSuretyRequired());
            }
            if (dotAuthority.isSetBipdOnFile()) {
                System.out.println("  BIPD on file: " + dotAuthority.getBipdOnFile());
            }
            if (dotAuthority.isSetCargoOnFile()) {
                System.out.println("  Cargo on file: " + dotAuthority.getCargoOnFile());
            }
            if (dotAuthority.isSetBondOnFile()) {
                System.out.println("  Bond on file: " + dotAuthority.getBondOnFile());
            }
        }

        // DOT Insurance
        if (successData.isSetDotInsurance()) {
            final DotInsuranceType dotInsurance = successData.getDotInsurance();
            System.out.println("=== DOT Insurance ===");
            System.out.println("  Last Update: "
                    + dotInsurance.getLastUpdateOfDotInsurance().getTime().toString());

            for (final InsuranceRecord insuranceRecord : dotInsurance.getInsuranceRecordArray()) {
                System.out.println("  -- Record --");
                System.out.println("    Coverage Type: " + insuranceRecord.getCoverageType().toString());
                if (insuranceRecord.isSetBipdClass()) {
                    System.out.println("    BIPD Class: " + insuranceRecord.getBipdClass().toString());
                }
                System.out.println("    Form code: " + insuranceRecord.getFormCode().toString());
                System.out.println("    Policy number: " + insuranceRecord.getPolicyNumber());
                System.out.println("    Coverage from: " + insuranceRecord.getCoverageFrom());
                System.out.println("    Coverage to: " + insuranceRecord.getCoverageTo());
                System.out.println("    Effective date: "
                        + insuranceRecord.getEffectiveDate().getTime().toString());
                if (insuranceRecord.isSetCanceledDate()) {
                    System.out.println("    Canceled date: " + insuranceRecord.getCanceledDate());
                }
                if (insuranceRecord.isSetInsuranceCarrier()) {
                    System.out.println("    Insurance carrier ID: "
                            + insuranceRecord.getInsuranceCarrier().getId());
                    System.out.println("    Insurance carrier company name: "
                            + insuranceRecord.getInsuranceCarrier().getCompanyName());
                    if (insuranceRecord.getInsuranceCarrier().isSetContact()) {
                        System.out.println("    Insurance carrier contact: "
                                + insuranceRecord.getInsuranceCarrier().getContact());
                    }
                    if (insuranceRecord.getInsuranceCarrier().isSetLocation()) {
                        System.out.println("    Insurance carrier location: ");
                        System.out.println(locationString(
                                insuranceRecord.getInsuranceCarrier().getLocation(), "      "));
                    }
                }
            }
        }

        // Insurance Certificates are not yet supported

        // SafeStat
        if (successData.isSetSafestat()) {
            final FmcsaSafeStat safestat = successData.getSafestat();
            System.out.println("=== SafeStat ===");
            System.out.println("  Last Update: " + safestat.getLastUpdateOfSafeStat().getTime().toString());

            if (safestat.isSetSeaDriver()) {
                System.out.format("  Driver: %.2f\n", safestat.getSeaDriver());
            }
            if (safestat.isSetSeaVehicle()) {
                System.out.format("  Vehicle: %.2f\n", safestat.getSeaVehicle());
            }
            if (safestat.isSetSeaManagement()) {
                System.out.format("  Management: %.2f\n", safestat.getSeaManagement());
            }
        }

        // Safety Rating
        if (successData.isSetSafetyRating()) {
            final FmcsaSafetyRating safetyRating = successData.getSafetyRating();
            System.out.println("=== Safety Rating ===");
            System.out.println("  Last Update: " + safetyRating.getLastUpdateOfSafety().getTime().toString());

            if (safetyRating.isSetRating()) {
                System.out.println("  Rating: " + safetyRating.getRating().toString());
            }
            if (safetyRating.isSetRatingDate()) {
                System.out.println("  Rating date: " + safetyRating.getRatingDate().getTime().toString());
            }
            if (safetyRating.isSetReviewType()) {
                System.out.println("  Review type: " + safetyRating.getReviewType());
            }
            if (safetyRating.isSetReviewDate()) {
                System.out.println("  Review date: " + safetyRating.getReviewDate().getTime().toString());
            }
        }

        // FMCSA Inspections
        if (successData.isSetFmcsaInspections()) {
            final FmcsaInspections inspections = successData.getFmcsaInspections();
            System.out.println("=== Inspections ===");
            System.out.println("  Last Update: "
                    + inspections.getLastUpdateOfInspections().getTime().toString());

            if (inspections.isSetTotalInspections()) {
                System.out.println("  Total inspections: " + inspections.getTotalInspections());
            }
            if (inspections.isSetVehicleInspections()) {
                System.out.println("  Vehicle inspections: "
                        + inspections.getVehicleInspections().getInspections());
                System.out.println("  Vehicle out-of-service: "
                        + inspections.getVehicleInspections().getOutOfService());
            }
            if (inspections.isSetDriverInspections()) {
                System.out.println("  Driver inspections: "
                        + inspections.getDriverInspections().getInspections());
                System.out.println("  Driver out-of-service: "
                        + inspections.getDriverInspections().getOutOfService());
            }
            if (inspections.isSetHazmatInspections()) {
                System.out.println("  Hazmat inspections: "
                        + inspections.getHazmatInspections().getInspections());
                System.out.println("  Hazmat out-of-service: "
                        + inspections.getHazmatInspections().getOutOfService());
            }
        }

        // FMCSA Crashes
        if (successData.isSetFmcsaCrashes()) {
            final FmcsaCrashes crashes = successData.getFmcsaCrashes();
            System.out.println("=== Inspections ===");
            System.out.println("  Last Update: " + crashes.getLastUpdateOfCrashes().getTime().toString());

            if (crashes.isSetCrashesFatal()) {
                System.out.println("  Fatal crashes: " + crashes.getCrashesFatal());
            }
            if (crashes.isSetCrashesInjury()) {
                System.out.println("  Injury crashes: " + crashes.getCrashesInjury());
            }
            if (crashes.isSetCrashesFatalOrInjury()) {
                System.out.println("  Fatal or injury crashes: " + crashes.getCrashesFatalOrInjury());
            }
            if (crashes.isSetCrashesTow()) {
                System.out.println("  Crashes requiring tow: " + crashes.getCrashesTow());
            }
            if (crashes.isSetCrashesHazmat()) {
                System.out.println("  Crashes involving hazmat: " + crashes.getCrashesHazmat());
            }
        }

        // CSA Safety Fitness
        if (successData.isSetCsa2010SafetyFitness()) {
            final Csa2010SafetyFitness csaSF = successData.getCsa2010SafetyFitness();
            System.out.println("=== CSA Safety Fitness ===");
            System.out.println("  Last Update: "
                    + csaSF.getLastUpdateOfCsa2010SafetyFitness().getTime().toString());

            for (final String interventionActivity : csaSF.getInterventionActivityArray()) {
                System.out.println("  Intervention activity: " + interventionActivity);
            }
            System.out.println("  Safety fitness determination: "
                    + csaSF.getSafetyFitnessDetermination().toString());
        }

        // CSA Basics
        if (successData.isSetCsa2010Basics()) {
            final Csa2010BasicMeasurements csaBasics = successData.getCsa2010Basics();
            System.out.println("=== CSA Basics ===");
            System.out.println("  Last Update: "
                    + csaBasics.getLastUpdateOfCsa2010Basics().getTime().toString());

            if (csaBasics.isSetInsuranceOrOtherViolation()) {
                System.out.println("  Insurance or other violation: "
                        + csaBasics.getInsuranceOrOtherViolation());
            }
            if (csaBasics.isSetInsuranceOrOtherIndicator()) {
                System.out.println("  Insurance or other indicator: "
                        + csaBasics.getInsuranceOrOtherIndicator());
            }
            for (final Measurement measurement : csaBasics.getMeasurementArray()) {
                System.out.println("  " + measurement.getBasic());
                if (measurement.isSetMeasure()) {
                    System.out.format("    Measure: %.1f\n", measurement.getMeasure());
                }
                if (measurement.isSetPercentile()) {
                    System.out.format("    Percentile: %.1f\n", measurement.getPercentile());
                }
                if (measurement.isSetNumberOfInspections()) {
                    System.out.println("    Number of inspections: " + measurement.getNumberOfInspections());
                }
                System.out.println("    Roadside alert: " + measurement.getRoadsideAlert());
                System.out.println("    Serious violation indicator: "
                        + measurement.getSeriousViolationIndicator());
                System.out.println("    BASIC indicator: " + measurement.getBasicIndicator());
            }
        }

        // Extended Profile
        if (successData.isSetExtendedProfile()) {
            final ExtendedProfile profile = successData.getExtendedProfile();
            System.out.println("=== Extended profile ===");
            System.out.println("  Last Update: "
                    + profile.getLastUpdateOfExtendedProfile().getTime().toString());

            if (profile.isSetCompanyBackground()) {
                final CompanyBackground background = profile.getCompanyBackground();
                if (background.isSetPrinciple()) {
                    System.out.println("  Principle: " + background.getPrinciple());
                }
                if (background.isSetPrinciple()) {
                    System.out.println("  Principal title: " + background.getPrincipalTitle());
                }
                if (background.isSetPrimaryContact()) {
                    System.out.println("  Primary contact: " + background.getPrimaryContact());
                }
                if (background.isSetPhone()) {
                    System.out.println("  Phone: " + phoneString(background.getPhone()));
                }
                if (background.isSetFax()) {
                    System.out.println("  Fax: " + phoneString(background.getFax()));
                }
                if (background.isSetEmailAddress()) {
                    System.out.println("  Email: " + background.getEmailAddress());
                }
                if (background.isSetWebsite()) {
                    System.out.println("  Website: " + background.getWebsite());
                }
                if (background.isSetYearFounded()) {
                    System.out.println("  Year founded: " + background.getYearFounded());
                }

                // Optional element, but defaults to false if omitted, as described in schema documentation
                System.out.println("  Woman owned: " + background.getIsWomanOwned());
                // Optional element, but defaults to false if omitted, as described in schema documentation
                System.out.println("  Minority owned: " + background.getIsMinorityOwned());
            }

            if (profile.isSetFleet()) {
                final Fleet fleet = profile.getFleet();
                if (fleet.isSetSatelliteTracking()) {
                    System.out.println("  Satellite tracking: " + fleet.getSatelliteTracking());
                }
                if (fleet.isSetNumberOfTeams()) {
                    System.out.println("  Number of teams: " + fleet.getNumberOfTeams());
                }
                // Optional elements, but default to false if omitted, as described in schema documentation
                System.out.println("  Has extra-wide vans: " + fleet.getHasXWideVans());
                System.out.println("  Has extra-long vans: " + fleet.getHasXLongVans());
                System.out.println("  Has extra-long reefers: " + fleet.getHasXLongReefers());
                System.out.println("  Has freezer reefers: " + fleet.getHasFreezerReefers());
                System.out.println("  Has Refrigerated only reefers: "
                        + fleet.getHasRefrigeratedOnlyReefers());
                System.out.println("  Insurance covers all drivers: " + fleet.getInsuranceCoversAllDrivers());
                System.out.println("  Insurance covers all trucks: " + fleet.getInsuranceCoversAllTrucks());

                for (final Truck truck : fleet.getTruckArray()) {
                    System.out.println("    -- Truck --");
                    System.out.println("    Code: " + truck.getCode());
                    System.out.println("    Type: " + truck.getType());
                    System.out.println("    Number of: " + truck.getNumberOf());
                }
            }

            if (profile.isSetServices()) {
                final Services services = profile.getServices();
                for (final String specialService : services.getSpecialServicesArray()) {
                    System.out.println("  Special service: " + specialService);
                }
                // Optional elements, but default to false if omitted, as described in schema documentation
                System.out.println("  Handles overweight loads: " + services.getHandlesOverweightLoads());
                System.out.println("  Handles long loads: " + services.getHandlesLongLoads());
                System.out.println("  Handles wide loads: " + services.getHandlesWideLoads());
                System.out.println("  Handles EDI: " + services.getHandlesEdi());
                System.out.println("  Responsible care: " + services.getIsResponsibleCare());
                System.out.println("  Has garment hanging: " + services.getHasGarmentHanging());
                System.out.println("  Has spotted trailers: " + services.getHasSpottedTrailers());
                System.out.println("  Does trailer exchange: " + services.getDoesTrailerExchange());
                System.out.println("  Does warehousing: " + services.getDoesWarehousing());
                System.out.println("  Does LTL: " + services.getDoesLTL());
                System.out.println("  Does multi-stops: " + services.getDoesMultiStops());
                System.out.println("  Does parcels: " + services.getDoesParcels());
                System.out.println("  Carries alchohol: " + services.getCarriesAlcohol());
                System.out.println("  Carries cigarettes: " + services.getCarriesCigarettes());
                System.out.println("  Carries ammunition or explosives: "
                        + services.getCarriesAmmunitionExplosives());
                System.out.println("  Carries cosmetics: " + services.getCarriesCosmetics());
                System.out.println("  Carries garments: " + services.getCarriesGarments());
                System.out.println("  Carries hazmat: " + services.getCarriesHazmat());
            }

            if (profile.isSetTerritory()) {
                // Optional elements, but default to false if omitted, as described in schema documentation
                System.out.println("  CSA Approved: " + profile.getTerritory().getIsCsaApproved());
                System.out.println("  FAST certified: " + profile.getTerritory().getIsFastCertified());
                System.out.println("  Provides service to Mexico: "
                        + profile.getTerritory().getServiceToMexico());

                if (profile.getTerritory().sizeOfStatesProvincesArray() > 0) {
                    final StateProvince.Enum[] states = profile.getTerritory().getStatesProvincesArray();
                    final StringBuilder sb = new StringBuilder();
                    sb.append(states[0]);
                    for (int i = 1; i < states.length; ++i) {
                        sb.append(',').append(states[i]);
                    }
                    System.out.println("  Service to: " + sb.toString());
                }

                for (final LaneType lane : profile.getTerritory().getLaneArray()) {
                    System.out.println("  Operates on: " + endpointString(lane.getOrigin()) + " -> "
                            + endpointString(lane.getDestination()));
                }
            }
        }

        // insurance certificate
        if (successData.getInsuranceCertificates() != null) {
            System.out.println("=== Insurnace Certificates ===");
            final InsuranceCertificates certificates = successData.getInsuranceCertificates();
            for (int i = 0; i < certificates.sizeOfCoverageArray(); ++i) {
                final InsuranceCertificate certificate = certificates.getCoverageArray(i);

                if (certificate.getAgentFax() != null) {
                    System.out.println("  Agent Fax country code: "
                            + certificate.getAgentFax().getCountryCode());
                    System.out.println("  Agent Fax number: " + certificate.getAgentFax().getNumber());
                    System.out.println("  Agent Fax extension: " + certificate.getAgentFax().getExtension());
                }

                System.out.println("  Agent Name: " + certificate.getAgentName());

                if (certificate.getAgentPhone() != null) {
                    System.out.println("  Agent Phone country code: "
                            + certificate.getAgentPhone().getCountryCode());
                    System.out.println("  Agent Phone number: " + certificate.getAgentPhone().getNumber());
                    System.out.println("  Agent Phone extension: "
                            + certificate.getAgentPhone().getExtension());
                }

                System.out.println("  Canceled Date: "
                        + ((certificate.getCanceledDate() != null) ? certificate.getCanceledDate().getTime()
                                .toString() : "N/A"));
                System.out.println("  Certificate Id: " + certificate.getCertificateId());
                System.out.println("  Confidence: " + certificate.getConfidence());
                System.out.println("  Coverage Category: " + certificate.getCoverageCategory().toString());

                if (certificate.sizeOfCoverageDescriptionArray() > 0) {
                    System.out.println("  Coverage Description: ");
                    for (int k = 0; k < certificate.sizeOfCoverageDescriptionArray(); ++k) {
                        System.out.print(certificate.getCoverageDescriptionArray(k));
                    }
                }

                System.out.println("  Coverage Id: " + certificate.getCoverageId());

                for (int j = 0; j < certificate.sizeOfCoverageLimitArray(); ++j) {
                    final CoverageLimit limit = certificate.getCoverageLimitArray(j);
                    System.out.println("  Coverage Lmit amount: " + limit.getLimit().getAmount());
                    if (limit.getLimit().getCurrency() != null) {
                        System.out.println("  Coverage Limit currency: "
                                + limit.getLimit().getCurrency().toString());
                    }
                    System.out.println("  Coverage Limit type: " + limit.getType());
                }
                System.out.println("  Coverage Type: " + certificate.getCoverageType().toString());
                System.out.println("  Effective Date: "
                        + ((certificate.getEffectiveDate() != null) ? certificate.getEffectiveDate()
                                .getTime().toString() : "N/A"));
                System.out.println("  Expiration Date: "
                        + ((certificate.getExpirationDate() != null) ? certificate.getExpirationDate()
                                .getTime().toString() : "N/A"));
                System.out.println("  Certificate Image URL: "
                        + certificate.getInsuranceCertificateImageUrl());
                System.out.println("  Scheduled Auto: " + certificate.getIsScheduledAuto());
                System.out.println("  Last Certificate Update: "
                        + ((certificate.getLastCertificateUpdate() != null) ? certificate
                                .getLastCertificateUpdate().getTime().toString() : "N/A"));
                System.out.println("  Policy Number: " + certificate.getPolicyNumber());
                System.out.println("  Underwriter: " + certificate.getUnderwriter());

            }

            if (certificates.getMaxCargoLimit() != null) {
                if (certificates.getMaxCargoLimit().getCurrency() != null) {
                    System.out.println("  Max Cargo Limit Currency: "
                            + certificates.getMaxCargoLimit().getCurrency().toString());
                }
                System.out
                        .println("  Max Cargo Limit Amount: " + certificates.getMaxCargoLimit().getAmount());
            }

        }
    }

    private String locationString(final Location location, final String indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(indent);
        sb.append(location.getStreet());
        sb.append(' ');
        sb.append(location.getCity());
        if (location.isSetStateProvince()) {
            sb.append(' ');
            sb.append(location.getStateProvince().toString());
        }
        if (location.isSetPostalCode()) {
            sb.append(' ');
            sb.append(location.getPostalCode());
        }
        sb.append(' ');
        sb.append(location.getCountryCode().toString());
        if (location.isSetPhone()) {
            sb.append("\n" + indent);
            sb.append("  Phone: ");
            sb.append(phoneString(location.getPhone()));
        }
        if (location.isSetFax()) {
            sb.append("\n" + indent);
            sb.append("  Fax: ");
            sb.append(phoneString(location.getFax()));
        }
        return sb.toString();
    }

    private String phoneString(final PhoneNumber phoneNumber) {
        final StringBuilder sb = new StringBuilder();
        sb.append(phoneNumber.isSetCountryCode() ? (phoneNumber.getCountryCode() + " ") : "");
        sb.append(phoneNumber.getNumber());
        sb.append(phoneNumber.isSetExtension() ? phoneNumber.getExtension() : "");
        return sb.toString();
    }

    private String endpointString(final EndPoint endPoint) {
        if (endPoint.isSetMetroArea()) {
            return endPoint.getMetroArea().getName() + ',' + endPoint.getStateProvince().toString() + " ("
                    + endPoint.getMetroArea().getCode() + ')';
        }
        return endPoint.getStateProvince().toString();
    }

    public static void main(final String[] args) {
        run(args);
    }
}
