package com.objective.keystone.model.converters;

import javax.persistence.Converter;

import com.objective.keystone.model.LiveStatus;

import au.id.lagod.dm.persistence.EnumCaseConverter;

@Converter(autoApply=true)
public class LiveStatusConverter extends EnumCaseConverter<LiveStatus> {
	
	public LiveStatusConverter() {
		super(LiveStatus.class);
	}

}
