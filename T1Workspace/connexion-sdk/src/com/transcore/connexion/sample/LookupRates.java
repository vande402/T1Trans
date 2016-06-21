package com.transcore.connexion.sample;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

import cltool4j.GlobalConfigProperties;
import cltool4j.args4j.EnumAliasMap;

import com.tcore.tcoreTypes.ServiceError;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tfmiFreightMatching.CityAndState;
import com.tcore.tfmiFreightMatching.Place;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;
import com.tcore.tfmiRates.CurrentContractRateReport;
import com.tcore.tfmiRates.CurrentSpotRateReport;
import com.tcore.tfmiRates.HistoricContractRateReport;
import com.tcore.tfmiRates.HistoricSpotRateReport;
import com.tcore.tfmiRates.LookupHistoricContractRatesOperation;
import com.tcore.tfmiRates.LookupHistoricContractRatesRequest;
import com.tcore.tfmiRates.LookupHistoricContractRatesRequestDocument;
import com.tcore.tfmiRates.LookupHistoricContractRatesResponse;
import com.tcore.tfmiRates.LookupHistoricContractRatesResponseDocument;
import com.tcore.tfmiRates.LookupHistoricContractRatesSuccessData;
import com.tcore.tfmiRates.LookupHistoricSpotRatesOperation;
import com.tcore.tfmiRates.LookupHistoricSpotRatesRequest;
import com.tcore.tfmiRates.LookupHistoricSpotRatesRequestDocument;
import com.tcore.tfmiRates.LookupHistoricSpotRatesResponse;
import com.tcore.tfmiRates.LookupHistoricSpotRatesResponseDocument;
import com.tcore.tfmiRates.LookupHistoricSpotRatesSuccessData;
import com.tcore.tfmiRates.LookupRateOperation;
import com.tcore.tfmiRates.LookupRateRequest;
import com.tcore.tfmiRates.LookupRateRequestDocument;
import com.tcore.tfmiRates.LookupRateResponse;
import com.tcore.tfmiRates.LookupRateResponseDocument;
import com.tcore.tfmiRates.LookupRateSuccessData;
import com.tcore.tfmiRates.RateEquipmentCategory;
import com.tcore.tfmiRates.SpotRateReport;

/**
 * Rate lookup example.
 * 
 * currentLookup - Current Rate Estimated, including contract and spot rates.
 * 
 * historicSpotLookup - 13-month historic estimates for spot.
 * 
 * historicContractLookup - 13-month historic estimates for contract.
 * 
 */
public class LookupRates extends BaseSampleClient {
    private RateEquipmentCategory.Enum equipmentCategory = RateEquipmentCategory.VANS;

    @Override
    protected void run() throws Exception {
        final SessionToken sessionToken = loginUser1();

        currentLookup(sessionToken);
        historicContractLookup(sessionToken);
        historicSpotLookup(sessionToken);
    }

    private void currentLookup(final SessionToken sessionToken) throws RemoteException {

        final String origin = GlobalConfigProperties.singleton().getProperty("origin");
        final String destination = GlobalConfigProperties.singleton().getProperty("destination");

        final LookupRateRequestDocument lookupRateRequestDoc = LookupRateRequestDocument.Factory
                .newInstance();
        final LookupRateRequest request = lookupRateRequestDoc.addNewLookupRateRequest();

        final LookupRateOperation operation = request.addNewLookupRateOperations();
        operation.setOrigin(place(origin));
        operation.setDestination(place(destination));
        operation.setEquipment(equipmentCategory);
        operation.setIncludeContractRate(true);
        operation.setIncludeSpotRate(true);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);

        final LookupRateResponseDocument responseDoc = stub.lookupRate(lookupRateRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupRateResponse response = responseDoc.getLookupRateResponse();

        System.out.println("\n=== Current Rate Lookup ===");
        System.out
                .println("Type        | RateEst | RateLow | RateHigh | TotalEst | TotalLow | TotalHigh | Conf | Origin                    | Destination               | FuelSur | Contr | Moves | DaysBack ");
        for (int i = 0; i < response.sizeOfLookupRateResultsArray(); i++) {
            System.out.format("--- %s -> %s ---\n", origin, destination);

            if (response.getLookupRateResultsArray(i).isSetServiceError()) {
                System.out.println(summaryString(response.getLookupRateResultsArray(i).getServiceError()));
                System.out.println();
                continue;
            }

            final LookupRateSuccessData successData = response.getLookupRateResultsArray(i)
                    .getLookupRateSuccessData();

            if (successData.isSetSpotRate()) {
                final CurrentSpotRateReport report = successData.getSpotRate();
                printCurrentSummaryLine("Spot", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(),
                        report.getEstimatedLinehaulTotal(), report.getLowLinehaulTotal(),
                        report.getHighLinehaulTotal(), report.getConfidenceLevel(), report.getRatedLane()
                                .getOriginGeography(), report.getRatedLane().getDestinationGeography(),
                        report.getAverageFuelSurchargeRate(), report.getContributors(), report.getMoves(),
                        report.getDaysBack());
            }

            if (successData.isSetContractRate()) {
                final CurrentContractRateReport report = successData.getContractRate();
                printCurrentSummaryLine("Contract", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(), 0, 0, 0, 0, report
                                .getRatedLane().getOriginGeography(), report.getRatedLane()
                                .getDestinationGeography(), report.getAverageFuelSurchargeRate(),
                        report.getContributors(), report.getMoves(), 0);

            }

            if (successData.isSetYourRate()) {
                final SpotRateReport report = successData.getYourRate();
                printCurrentSummaryLine("Contributor", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(),
                        report.getEstimatedLinehaulTotal(), report.getLowLinehaulTotal(),
                        report.getHighLinehaulTotal(), report.getConfidenceLevel(), null, null, 0, 0, 0, 0);

            }
        }
    }

    private void historicSpotLookup(final SessionToken sessionToken) throws RemoteException {
        final String origin = GlobalConfigProperties.singleton().getProperty("origin");
        final String destination = GlobalConfigProperties.singleton().getProperty("destination");

        final LookupHistoricSpotRatesRequestDocument requestDoc = LookupHistoricSpotRatesRequestDocument.Factory
                .newInstance();
        final LookupHistoricSpotRatesRequest request = requestDoc.addNewLookupHistoricSpotRatesRequest();
        final LookupHistoricSpotRatesOperation operation = request.addNewLookupHistoricSpotRatesOperation();
        operation.setOrigin(place(origin));
        operation.setDestination(place(destination));
        operation.setEquipment(equipmentCategory);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupHistoricSpotRatesResponseDocument responseDoc = stub.lookupHistoricSpotRates(requestDoc,
                null, null, sessionHeaderDocument(sessionToken));
        final LookupHistoricSpotRatesResponse response = responseDoc.getLookupHistoricSpotRatesResponse();

        System.out.println("\n=== Historic Spot Lookup ===");
        System.out.println("When    | RateEst | RateLow | RateHigh | TotalEst | TotalLow | TotalHigh | Conf");

        if (response.getLookupHistoricSpotRatesResult().isSetServiceError()) {
            System.out.println(summaryString(response.getLookupHistoricSpotRatesResult().getServiceError()));
            return;
        }

        final LookupHistoricSpotRatesSuccessData successData = response.getLookupHistoricSpotRatesResult()
                .getLookupHistoricSpotRatesSuccessData();

        for (int i = 0; i < successData.sizeOfMonthlyReportArray(); i++) {
            final HistoricSpotRateReport report = successData.getMonthlyReportArray(i);
            System.out.format("%4d-%02d | %7.2f | %7.2f | %8.2f | %8.2f | %8.2f | %9.2f | %4d\n", report
                    .getWhen().getYear(), report.getWhen().getMonth(), report.getEstimatedLinehaulRate(),
                    report.getLowLinehaulRate(), report.getHighLinehaulRate(), report
                            .getEstimatedLinehaulTotal(), report.getLowLinehaulTotal(), report
                            .getHighLinehaulTotal(), report.getConfidenceLevel());
        }
    }

    private void historicContractLookup(final SessionToken sessionToken) throws RemoteException {
        final String origin = GlobalConfigProperties.singleton().getProperty("origin");
        final String destination = GlobalConfigProperties.singleton().getProperty("destination");

        final LookupHistoricContractRatesRequestDocument requestDoc = LookupHistoricContractRatesRequestDocument.Factory
                .newInstance();
        final LookupHistoricContractRatesRequest request = requestDoc
                .addNewLookupHistoricContractRatesRequest();
        final LookupHistoricContractRatesOperation operation = request
                .addNewLookupHistoricContractRatesOperation();
        operation.setOrigin(place(origin));
        operation.setDestination(place(destination));
        operation.setEquipment(equipmentCategory);

        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupHistoricContractRatesResponseDocument responseDoc = stub.lookupHistoricContractRates(
                requestDoc, null, null, sessionHeaderDocument(sessionToken));
        final LookupHistoricContractRatesResponse response = responseDoc
                .getLookupHistoricContractRatesResponse();

        System.out.println("\n=== Historic Contract Lookup ===");
        System.out.println("When    | RateEst | RateLow | RateHigh");
        if (response.getLookupHistoricContractRatesResult().isSetServiceError()) {
            System.out.println(summaryString(response.getLookupHistoricContractRatesResult()
                    .getServiceError()));
            return;
        }

        final LookupHistoricContractRatesSuccessData successData = response
                .getLookupHistoricContractRatesResult().getLookupHistoricContractRatesSuccessData();

        for (int i = 0; i < successData.sizeOfMonthlyReportArray(); i++) {
            final HistoricContractRateReport report = successData.getMonthlyReportArray(i);
            System.out.format("%4d-%02d | %7.2f | %7.2f | %8.2f\n", report.getWhen().getYear(), report
                    .getWhen().getMonth(), report.getEstimatedLinehaulRate(), report.getLowLinehaulRate(),
                    report.getHighLinehaulRate());
        }
    }

    public static void main(final String[] args) {
        run(args);
    }

    private void printCurrentSummaryLine(final String type, final float rateEst, final float rateLow,
            final float rateHigh, final float totalEst, final float totalLow, final float totalHigh,
            final int confidence, final String ratedOrigin, final String ratedDestination,
            final float fuelSurcharge, final int contributors, final int moves, final int daysBack) {
        System.out
                .format("%-11s | %7.2f | %7.2f | %8.2f | %8s | %8s | %9s | %4s | %-25s | %-25s | %7.2f | %5s | %5s | %8s\n",
                        type, rateEst, rateLow, rateHigh, asString(totalEst), asString(totalLow),
                        asString(totalHigh), asString(confidence), ratedOrigin, ratedDestination,
                        fuelSurcharge, asString(contributors), asString(moves), asString(daysBack));
    }

    private String asString(final float f) {
        return f == 0 ? "-" : String.format("%.2f", f);
    }

    private String asString(final int i) {
        return i == 0 ? "-" : Integer.toString(i);
    }

    /**
     * Enumeration of all supported lookup types.
     */
    public static enum LookupType {
        Contract("c"), Spot("s"), Contributor("m"), All("a"), Historic_spot("hs"), Historic_contract("hc");

        private LookupType(final String... aliases) {
            EnumAliasMap.singleton().addAliases(this, aliases);
        }
    }

    protected Place place(final String place) {
        final Place p = Place.Factory.newInstance();

        // US Postal code
        if (US_POSTAL_CODE_PATTERN.matcher(place).matches()) {
            p.addNewPostalCode().setCode(place);
            p.getPostalCode().setCountry(com.tcore.tcoreTypes.CountryCode.US);
        }
        // Canadian Postal code
        else if (CA_POSTAL_CODE_PATTERN.matcher(place).matches()) {
            p.addNewPostalCode().setCode(place.toUpperCase());
            p.getPostalCode().setCountry(com.tcore.tcoreTypes.CountryCode.CA);
        }
        // City/state
        else {
            p.setCityAndState(cityAndState(place));
        }
        return p;
    }

    private final static Pattern US_POSTAL_CODE_PATTERN = Pattern.compile("[0-9]{5}(-[0-9]{4})?");

    private final static Pattern CA_POSTAL_CODE_PATTERN = Pattern
            .compile("[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]");

    protected CityAndState cityAndState(final String commaDelimitedCityAndState) {
        final String[] split = commaDelimitedCityAndState.split(" *, *");
        if (split.length < 2 || split.length > 3) {
            throw new IllegalArgumentException(
                    "Expected comma-delimited city and state (e.g. 'Boise,ID', 'Salem,AL,Lee')");
        }
        return cityAndState(split);
    }

    protected CityAndState cityAndState(final String[] cityState) {
        final CityAndState cityAndState = CityAndState.Factory.newInstance();
        cityAndState.setCity(cityState[0]);
        cityAndState.setStateProvince(StateProvince.Enum.forString(cityState[1].toUpperCase()));
        if (cityState.length == 3) {
            cityAndState.setCounty(cityState[2]);
        }
        return cityAndState;
    }

    private String summaryString(final ServiceError serviceError) {
        if (serviceError.getDetailedMessage() == null) {
            return String.format("%s", serviceError.getMessage());
        }

        return String.format("%s : %s", serviceError.getMessage(), serviceError.getDetailedMessage());
    }

}
