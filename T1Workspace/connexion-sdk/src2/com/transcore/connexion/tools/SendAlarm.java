package com.transcore.connexion.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cltool4j.BaseCommandlineTool;
import cltool4j.args4j.Option;

public class SendAlarm extends BaseCommandlineTool {

    @Option(name = "-u", required = true, metaVar = "url", usage = "Target server URL")
    String url;

    @Option(name = "-a", metaVar = "ID", usage = "Source Alarm ID")
    String alarmId = "TA999999";

    @Option(name = "-b", metaVar = "ID", usage = "Basis Asset ID")
    String basisAssetId = "TS000000";

    @Override
    public void run() throws Exception {
        final HttpURLConnection urlCon = (HttpURLConnection) new URL(url).openConnection();
        urlCon.setRequestMethod("POST");
        urlCon.setRequestProperty("Content-Type", "text/xml");
        urlCon.setDoOutput(true);
        urlCon.setDoInput(true);
        urlCon.getOutputStream().write(createMatch().getBytes());
        urlCon.getOutputStream().close();

        System.out.println(new String(readInputStream(urlCon.getInputStream())));
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

    private String createMatch() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        final Date now = Calendar.getInstance().getTime();
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        final Date tomorrow = c.getTime();

        final StringBuffer sb = new StringBuffer(8196);
        sb.append("<soap:Envelope\n");
        sb.append("    xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"\n");
        sb.append("    xmlns:tfmialarm=\"http://www.tcore.com/TfmiAlarmMatch.xsd\"\n");
        sb.append("    xmlns:types=\"http://www.tcore.com/TcoreTypes.xsd\"\n");
        sb.append("    xmlns:tfmi=\"http://www.tcore.com/TfmiFreightMatching.xsd\">\n");
        sb.append("  <soap:Body>\n");
        sb.append("    <tfmialarm:alarmMatchNotification>\n");
        sb.append("      <tfmialarm:alarmId>" + alarmId + "</tfmialarm:alarmId>\n");
        sb.append("      <tfmialarm:basisAssetId>" + basisAssetId + "</tfmialarm:basisAssetId>\n");
        sb.append("      <tfmialarm:basisAssetPostersReferenceId>DS111112</tfmialarm:basisAssetPostersReferenceId>\n");
        sb.append("      <tfmialarm:cancelled>false</tfmialarm:cancelled>\n");
        sb.append("      <tfmialarm:match>\n");
        sb.append("        <tfmi:asset>\n");
        sb.append("          <tfmi:assetId>TE000111</tfmi:assetId>\n");
        sb.append("          <tfmi:status>\n");
        sb.append("            <tfmi:userId>130345</tfmi:userId>\n");
        sb.append("            <tfmi:startDate>" + sdf.format(now) + "</tfmi:startDate>\n");
        sb.append("            <tfmi:endDate>" + sdf.format(tomorrow) + "</tfmi:endDate>\n");
        sb.append("            <tfmi:created>\n");
        sb.append("              <types:user>130345</types:user>\n");
        sb.append("              <types:date>" + sdf.format(now) + "</types:date>\n");
        sb.append("            </tfmi:created>\n");
        sb.append("            <tfmi:updated>\n");
        sb.append("              <types:user>130345</types:user>\n");
        sb.append("              <types:date>" + sdf.format(now) + "</types:date>\n");
        sb.append("            </tfmi:updated>\n");
        sb.append("            <tfmi:expired>false</tfmi:expired>\n");
        sb.append("          </tfmi:status>\n");
        sb.append("          <tfmi:equipment>\n");
        sb.append("            <tfmi:equipmentType>Van</tfmi:equipmentType>\n");
        sb.append("            <tfmi:origin>\n");
        sb.append("              <tfmi:namedCoordinates>\n");
        sb.append("                <tfmi:latitude>42.35833</tfmi:latitude>\n");
        sb.append("                <tfmi:longitude>-71.06028</tfmi:longitude>\n");
        sb.append("                <tfmi:city>Boston</tfmi:city>\n");
        sb.append("                <tfmi:stateProvince>MA</tfmi:stateProvince>\n");
        sb.append("              </tfmi:namedCoordinates>\n");
        sb.append("            </tfmi:origin>\n");
        sb.append("            <tfmi:destination>\n");
        sb.append("              <tfmi:open/>\n");
        sb.append("            </tfmi:destination>\n");
        sb.append("          </tfmi:equipment>\n");
        sb.append("          <tfmi:postersReferenceId>PosterId</tfmi:postersReferenceId>\n");
        sb.append("          <tfmi:ltl>1</tfmi:ltl>\n");
        sb.append("          <tfmi:comments>Comment 1</tfmi:comments>\n");
        sb.append("          <tfmi:comments>Comment 2</tfmi:comments>\n");
        sb.append("          <tfmi:count>1</tfmi:count>\n");
        sb.append("          <tfmi:dimensions>\n");
        sb.append("            <tfmi:lengthFeet>48</tfmi:lengthFeet>\n");
        sb.append("            <tfmi:weightPounds>50000</tfmi:weightPounds>\n");
        sb.append("          </tfmi:dimensions>\n");
        sb.append("          <tfmi:stops>1</tfmi:stops>\n");
        sb.append("          <tfmi:availability>\n");
        sb.append("            <tfmi:earliest>" + sdf.format(now) + "</tfmi:earliest>\n");
        sb.append("            <tfmi:latest>" + sdf.format(tomorrow) + "</tfmi:latest>\n");
        sb.append("          </tfmi:availability>\n");
        sb.append("        </tfmi:asset>\n");
        sb.append("        <tfmi:originDeadhead>\n");
        sb.append("          <tfmi:miles>37</tfmi:miles>\n");
        sb.append("          <tfmi:method>Road</tfmi:method>\n");
        sb.append("        </tfmi:originDeadhead>\n");
        sb.append("        <tfmi:callback>\n");
        sb.append("          <tfmi:userId>40025</tfmi:userId>\n");
        sb.append("          <tfmi:phone>\n");
        sb.append("            <tfmi:phone>\n");
        sb.append("              <types:number>5055055005</types:number>\n");
        sb.append("            </tfmi:phone>\n");
        sb.append("          </tfmi:phone>\n");
        sb.append("          <tfmi:name>\n");
        sb.append("            <types:firstName>Bob</types:firstName>\n");
        sb.append("            <types:lastName>Pringle</types:lastName>\n");
        sb.append("            <types:initials>BP</types:initials>\n");
        sb.append("          </tfmi:name>\n");
        sb.append("          <tfmi:companyName>BP Transport</tfmi:companyName>\n");
        sb.append("          <tfmi:displayCompany>BP</tfmi:displayCompany>\n");
        sb.append("          <tfmi:postersStateProvince>NJ</tfmi:postersStateProvince>\n");
        sb.append("        </tfmi:callback>\n");
        sb.append("      </tfmialarm:match>\n");
        sb.append("    </tfmialarm:alarmMatchNotification>\n");
        sb.append("  </soap:Body>\n");
        sb.append("</soap:Envelope>\n");

        return sb.toString();
    }

    public static void main(final String[] args) {
        run(args);
    }
}
