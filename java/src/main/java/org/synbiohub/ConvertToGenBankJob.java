package org.synbiohub;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.sbolstandard.core2.*;

import javax.xml.namespace.QName;

public class ConvertToGenBankJob extends Job
{
	public String sbolFilename;
	public boolean requireComplete;
	public boolean requireCompliant;
	public boolean enforceBestPractices;
	public boolean typesInURI;
	public boolean keepGoing;
	public String topLevelURI;

	public void execute() throws Exception
	{
		ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();
		
		SBOLDocument doc = SBOLValidateSilent.validate(
				new PrintStream(logOutputStream),
				new PrintStream(errorOutputStream),
				sbolFilename,
				"",
				requireComplete,
				requireCompliant, 
				enforceBestPractices,
				typesInURI,
				"",
				keepGoing,
				"",
				"",
				sbolFilename,
				topLevelURI,
				true,
				false,
				false,
				"",
				false,
				false);
		
		String log = new String(logOutputStream.toByteArray(), StandardCharsets.UTF_8);
		String errorLog = new String(errorOutputStream.toByteArray(), StandardCharsets.UTF_8);

		if(doc == null)
		{
			finish(new ConvertToGenBankResult(this, false, "", log, errorLog));
			return;
		}

		finish(new ConvertToGenBankResult(this, true, "", log, errorLog));

	}
}
