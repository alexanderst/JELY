/**
 * This file is part of the JELY distribution (https://github.com/mad-lab-fau/JELY).
 * Copyright (c) 2015-2020 Machine Learning and Data Analytics Lab, Friedrich-Alexander-Universität Erlangen-Nürnberg (FAU).
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * <p>
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package jely.io;

import jely.Ecg;
import jely.EcgLead;
import jely.LeadConfiguration;
import ru.mipt.edf.EDFHeader;
import ru.mipt.edf.EDFParser;
import ru.mipt.edf.EDFParserResult;
import ru.mipt.edf.EDFSignal;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EdfEcgFile extends Ecg {

    public EdfEcgFile(String pathToEdfFile) {
        leadInfo = new LeadConfiguration(3);
        ecgLeads = new ArrayList<>(getNumLeads());

        for (int i = 0; i < 3; ++i) {
            leadInfo.add(EcgLead.UNKNOWN);
        }

        try (InputStream is = new BufferedInputStream(new FileInputStream(new File(pathToEdfFile)))) {
            EDFParserResult result = EDFParser.parseEDF(is);

            EDFHeader header = result.getHeader();
            EDFSignal signal = result.getSignal();

            DateFormat formatter = new SimpleDateFormat("DD.MM.YY");
            firstSampleDate = (Date)formatter.parse(header.getStartDate());
            date = firstSampleDate;
            formatter = new SimpleDateFormat("HH.MM.SS");
            Date timeAsDate = (Date)formatter.parse(header.getStartTime());
            firstSampleTimestamp = timeAsDate.getTime();
            samplingRate = header.getNumberOfSamples()[0];


            init(signal.getValuesInUnits()[0].length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
