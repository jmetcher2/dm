package com.objective.keystone.model.converters;

import javax.persistence.Converter;

import com.objective.keystone.model.event.InviteStatus;

import au.id.lagod.dm.persistence.EnumCaseConverter;

@Converter(autoApply=true)
public class InviteStatusConverter extends EnumCaseConverter<InviteStatus> {
	
	public InviteStatusConverter() {
		super(InviteStatus.class);
	}

}
