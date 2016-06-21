package com.transcore.connexion.sample.alarm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.transport.http.AxisServlet;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.xmlsoap.schemas.soap.envelope.EnvelopeDocument;

import com.tcore.www.tfmialarmmatch_xsd.AlarmMatchNotification;
import com.tcore.www.tfmialarmmatch_xsd.AlarmMatchNotificationDocument;
import com.tcore.www.tfmialarmmatch_xsd.AlarmNotification;
import com.tcore.www.tfmialarmmatch_xsd.AlarmResponseDocument;
import com.tcore.www.tfmialarmmatch_xsd.AlarmTerminationNotification;
import com.tcore.www.tfmialarmmatch_xsd.AlarmTerminationNotificationDocument;

/**
 * This servlet mocks the functionality provided by {@link AxisServlet}, specifically for handling alarm match
 * and alarm termination notifications. This simple implementation is used for demonstration purposes in TJWS
 * (Tiny Java Web Server), to avoid having to load the entire Axis distribution.
 * 
 * @author Aaron Dunlop
 * 
 */
public class MockAxisServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    final SampleAlarmMatchService service = new SampleAlarmMatchService();

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        try {
            final byte[] buf = readInputStream(request.getInputStream());
            final EnvelopeDocument envelope = (EnvelopeDocument) XmlObject.Factory
                    .parse(new ByteArrayInputStream(buf));
            final AlarmNotification notification = body(envelope);
            response.setContentType("text/xml");

            if (notification instanceof AlarmMatchNotification) {
                final AlarmMatchNotificationDocument doc = AlarmMatchNotificationDocument.Factory
                        .newInstance();
                doc.setAlarmMatchNotification((AlarmMatchNotification) notification);
                response.getOutputStream().write(
                        responseEnvelope(service.alarmMatchNotification(doc)).xmlText().getBytes());
            } else {
                final AlarmTerminationNotificationDocument doc = AlarmTerminationNotificationDocument.Factory
                        .newInstance();
                doc.setAlarmTerminationNotification((AlarmTerminationNotification) notification);
                response.getOutputStream().write(
                        responseEnvelope(service.alarmTerminationNotification(doc)).xmlText().getBytes());
            }

        } catch (final XmlException e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().close();
        }
    }

    private byte[] readInputStream(final InputStream is) throws IOException {
        // Start with a 10k buffer - most requests are smaller than that
        final ByteArrayOutputStream bos = new ByteArrayOutputStream(10240);
        // Read in 512-byte chunks
        final byte[] buf = new byte[512];
        int i = 0;
        while ((i = is.read(buf)) > 0) {
            bos.write(buf, 0, i);
        }
        is.close();
        return bos.toByteArray();
    }

    private AlarmNotification body(final EnvelopeDocument requestEnvelope) {

        final XmlCursor bodyCursor = requestEnvelope.getEnvelope().getBody().newCursor();

        try {
            bodyCursor.toFirstChild();
            return (AlarmNotification) bodyCursor.getObject();
        } finally {
            bodyCursor.dispose();
        }
    }

    private EnvelopeDocument responseEnvelope(final AlarmResponseDocument responseDoc) {

        final EnvelopeDocument envelope = EnvelopeDocument.Factory.newInstance();

        final XmlCursor bodyCursor = envelope.addNewEnvelope().addNewBody().newCursor();
        bodyCursor.toNextToken();
        final XmlCursor responseCursor = responseDoc.getAlarmResponse().newCursor();
        responseCursor.copyXml(bodyCursor);

        responseCursor.dispose();
        bodyCursor.dispose();

        return envelope;
    }

}
