package com.cowboy76.guesthouse.batch.mapper;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.cowboy76.guesthouse.batch.model.Report;

public class ReportFileSetMapper implements FieldSetMapper<Report> {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	public Report mapFieldSet(FieldSet fieldSet) throws BindException {

		// TODO Autogenerated method stub

		Report report = new Report();
		report.setId(fieldSet.readInt(0));
		report.setSales(fieldSet.readBigDecimal(1));
		report.setQty(fieldSet.readInt(2));
		report.setStaffName(fieldSet.readString(3));
		String date = fieldSet.readString(4);

		try {
			report.setDate(format.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return report;

	}

}
