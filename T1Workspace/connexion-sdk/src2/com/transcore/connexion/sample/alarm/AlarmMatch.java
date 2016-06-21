package com.transcore.connexion.sample.alarm;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Enumeration;

import org.apache.axis2.AxisFault;

import cltool4j.GlobalConfigProperties;

import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tfmiFreightMatching.Alarm;
import com.tcore.tfmiFreightMatching.LookupAlarmOperation;
import com.tcore.tfmiFreightMatching.LookupAlarmRequestDocument;
import com.tcore.tfmiFreightMatching.LookupAlarmResponseDocument;
import com.tcore.tfmiFreightMatching.LookupAlarmResult;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;
import com.tcore.tfmiFreightMatching.UpdateAlarmUrlRequestDocument;
import com.transcore.connexion.sample.BaseSampleClient;

/***************************************************************************
 * This application demonstrates a simple use of AlarmMatch.
 * <p>
 * AlarmMatch is implemented by means of a reverse HTTP server. The process goes as follows:
 * <ul>
 * <li>
 * The client sets up an HTTP server to receive AlarmMatch events. The server is set up to activate the
 * servlet if/when a suitable request is sent to the server.
 * <li>
 * The client posts an alarm, supplying the IP address and port for the client's HTTP server.
 * <li>
 * When an alarm event is generated, Connexion sends an HTTP POST request to the client's HTTP server. Alarm
 * events are of two types: Alarm matches, which invoke the servlet's onAlarmMatch method; Alarm terminations,
 * which invoke the servelet's onAlarmTermination method.
 * </ul>
 * This client creates and activates a separate thread to start the HTTP server. Then, as login user 1, it
 * posts a truck and sets an alarm on the truck. Finally, and after waiting a few seconds, as login user 2,
 * this client posts a matching shipment. An alarm match event should be sent to the servlet, which will print
 * information about the alarm match event.
 * <p>
 * For more detailed comments about posting assets such as trucks and loads, see the posting sample client,
 * SimplePostSampleClient.java.
 * 
 * @author Aaron Dunlop
 * 
 ***************************************************************************/
public class AlarmMatch extends BaseSampleClient {

    @Override
    public void run() throws RemoteException {
        try {
            // Start the servlet container. Most production deployments will use a full-featured servlet
            // container (e.g., Tomcat, WebLogic, etc.), but for demonstration purposes we use a very simple
            // container.
            final SampleAlarmMatchServer server = new SampleAlarmMatchServer();
            final Thread httpServerThread = new Thread(server);
            httpServerThread.start();
            // Wait a bit to allow the container to initialize
            sleep(2000);

            // Login as user one and clean up any existing assets
            final SessionToken sessionToken1 = loginUser1();
            deleteAllAssets(sessionToken1);

            // Set the alarm URL to point to the current host. This is generally unnecessary unless the
            // callback URL changes frequently. If the URL is relatively static, TransCore support can set it,
            // and the user code can skip this call.
            final String alarmUrl = GlobalConfigProperties.singleton().containsKey("alarm.url") ? GlobalConfigProperties
                    .singleton().getProperty("alarm.url") : localAlarmUrl(server);

            final UpdateAlarmUrlRequestDocument updateAlarmUrlRequestDoc = UpdateAlarmUrlRequestDocument.Factory
                    .newInstance();
            updateAlarmUrlRequestDoc.addNewUpdateAlarmUrlRequest().addNewUpdateAlarmUrlOperation()
                    .setAlarmUrl(alarmUrl);

            // Validate the request document before executing the operation
            validate(updateAlarmUrlRequestDoc);

            final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
            stub.updateAlarmUrl(updateAlarmUrlRequestDoc, null, null, sessionHeaderDocument(sessionToken1));

            // Post a truck and set an alarm for matching shipments. Note: it is common to set an alarm on an
            // asset after posting, using CreateAlarmRequest; this call combines the two.
            System.out.println("\n=== Posting equipment as user 1 with alarm ===");
            postEquipment(sessionToken1, "Salina", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f, true);

            // Query the alarms for user 1 (demonstrating alarm query)
            queryAlarms(sessionToken1);

            // Login as a second user (in a separate company)
            final SessionToken sessionToken2 = loginUser2();
            deleteAllAssets(sessionToken2);

            // As user 2, post a shipment matching the alarm set by user 1
            System.out.println("\n=== Posting a shipment as user 2. Expecting alarm match notification ===");
            postShipment(sessionToken2, "Salina", StateProvince.KS, "Nome", StateProvince.AK, 64.5f,
                    -165.404f);
            sleep(3000);

            // Delete user 2's shipments, which should send an alarm cancellation event
            System.out.println("\n=== Deleting shipment. Expecting alarm cancellation notification ===");
            deleteAllAssets(sessionToken2);
            sleep(3000);

            // Delete the basis asset owned by user 1. We expect the alarm server to be notified that the
            // alarm based on that asset is terminated.
            System.out.println("\n=== Deleting equipment. Expecting alarm termination notification ===");
            deleteAllAssets(sessionToken1);
            sleep(3000);

            server.stop();
            httpServerThread.join();

        } catch (final SocketException e) {
            e.printStackTrace();
        } catch (final AxisFault fault) {
            fault.printStackTrace();
        } catch (final InterruptedException ignore) {
        }
    }

    private String localAlarmUrl(final SampleAlarmMatchServer server) throws SocketException {

        // Find a local interface address (which isn't localhost or a VPN
        // connection) and format the alarm URL
        for (final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e
                .hasMoreElements();) {

            final NetworkInterface ni = e.nextElement();
            if (ni.getDisplayName().toLowerCase().startsWith("vpn")) {
                continue;
            }

            // Find an IPv4 address
            final InetAddress ia = ipv4Address(ni);
            if (ia == null) {
                continue;
            }

            final byte[] address = ia.getAddress();
            return String.format("http://%d.%d.%d.%d:%d%s", address[0] & 0xff, address[1] & 0xff,
                    address[2] & 0xff, address[3] & 0xff, server.getPort(), server.getPath());
        }
        throw new IllegalArgumentException("Unable to find a usable interface");
    }

    private InetAddress ipv4Address(final NetworkInterface networkInterface) {
        for (final Enumeration<InetAddress> e = networkInterface.getInetAddresses(); e.hasMoreElements();) {
            final InetAddress ia = e.nextElement();
            if (ia instanceof Inet4Address && !ia.isLoopbackAddress()
                    && ia.getHostAddress().indexOf(":") == -1) {
                return ia;
            }
        }
        return null;
    }

    /**
     * Query my alarms.
     * 
     * @throws RemoteException
     */
    protected void queryAlarms(final SessionToken sessionToken) throws RemoteException {
        final LookupAlarmRequestDocument lookupRequestDoc = LookupAlarmRequestDocument.Factory.newInstance();
        final LookupAlarmOperation operation = lookupRequestDoc.addNewLookupAlarmRequest()
                .addNewLookupAlarmOperation();
        operation.addNewQueryAllMyAlarms();

        // Validate the request document before executing the operation
        validate(lookupRequestDoc);

        // Execute the lookup
        final TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);
        final LookupAlarmResponseDocument responseDoc = stub.lookupAlarm(lookupRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
        final LookupAlarmResult result = responseDoc.getLookupAlarmResponse().getLookupAlarmResult();

        // Check for errors (note - some more severe errors will result in an AxisFault instead)
        if (!result.isSetLookupAlarmSuccessData()) {
            throw new RemoteException("Lookup Request Failed: " + result.getServiceError().getMessage()
                    + " : " + result.getServiceError().getDetailedMessage());
        }

        // Output summaries of all alarms
        System.out.println("\n=== Alarms for user 1 ===");
        for (final Alarm alarm : result.getLookupAlarmSuccessData().getAlarmsArray()) {
            System.out.format("  AlarmId %s (AssetId %s)\n", alarm.getAlarmId(), alarm.getBasisAssetId());
        }
    }

    /**
     * Sleep (ignoring InterruptedException)
     * 
     * @param millis
     */
    protected void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignore) {
        }
    }

    public static void main(final String[] args) {
        run(args);
    }

    /***************************************************************************
     * This class starts an HTTP server for Alarm Match demonstration. It implements Runnable, in order to
     * show parallel execution of the Alarm Match server and the client thread that posts the assets that
     * cause alarm events to be sent to the server. It is intended that running this class's main() will
     * activate the server.
     * <p>
     * Configuration parameters, such as port number and HTTP request path are taken from global properties
     * specified via the -O arguments -O <name>=<value>, then from the properties file.
     * <p>
     * The caller should do something like the following to start a server:
     * <p>
     * <code>SampleAlarmMatchServer s = new SampleAlarmMatchServer();</code> <br>
     * <code>new Thread(s).start();</code>
     ***************************************************************************/
    public static class SampleAlarmMatchServer implements Runnable {

        private Acme.Serve.Serve tjwsServer;

        /**
         * This method is called to run the server in a thread separate from the thread that created this
         * instance.
         */
        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            System.out.println("Starting sample alarm match server. Port = " + getPort() + "  Path = "
                    + getPath());

            tjwsServer = new Acme.Serve.Serve();
            tjwsServer.arguments = GlobalConfigProperties.singleton();
            tjwsServer.arguments.put(Acme.Serve.Serve.ARG_NOHUP, "nohup");

            tjwsServer.addServlet(getPath(), MockAxisServlet.class.getName());
            tjwsServer.serve();
        }

        /**
         * Shutdown the server
         */
        public void stop() {
            if (tjwsServer != null) {
                try {
                    tjwsServer.notifyStop();
                    tjwsServer.destroyAllServlets();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getPort() {
            return GlobalConfigProperties.singleton().getIntProperty("port");
        }

        public String getPath() {
            return GlobalConfigProperties.singleton().getProperty("path");
        }
    }
}
