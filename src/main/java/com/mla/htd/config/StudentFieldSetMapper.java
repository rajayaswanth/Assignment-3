package com.mla.htd.config;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.context.properties.bind.BindException;

import com.mla.htd.entitty.Student;

public class StudentFieldSetMapper implements FieldSetMapper<Student> {

	@Override
	public Student mapFieldSet(FieldSet fieldSet) throws BindException {
		return new Student(fieldSet.readLong("id"),
				fieldSet.readString("name"),
				fieldSet.readString("course"),
				fieldSet.readString("teacher"));
	}
}
