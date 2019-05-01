package com.objective.keystone.model.event;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.objective.keystone.model.LiveStatus;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.ConsultFolder;

import au.id.lagod.dm.base.ChildDomainObject;

@Entity
@Table(name="publisher_event")
public class Event extends MappedEvent implements ChildDomainObject {

	protected Event() {}
	
	protected Event(Customer customer, EventType type, String name) {
		super();
		this.customer = customer;
		this.type = type;
		this.name = name;
		this.shortName = name;
		this.eTag = UUID.randomUUID().toString();
		this.folder = (ConsultFolder) customer.getFolders().getConsultRoot();
		this.liveStatus = LiveStatus.PUBLIC;
		this.inviteStatus = InviteStatus.PUBLIC;
		this.standaloneStatus = "both";
	}

	public void setArchiveRepresentations(LocalDateTime archiveRepresentations) {
		this.archiveRepresentations = archiveRepresentations;
	}

	public void setEndEvent(LocalDateTime endEvent) {
		this.endEvent = endEvent;
	}

	public void setInviteStatus(InviteStatus inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public void setLiveStatus(LiveStatus liveStatus) {
		this.liveStatus = liveStatus;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public void setPrivacyStatus(String privacyStatus) {
		this.privacyStatus = privacyStatus;
	}

	public void setStartEvent(LocalDateTime startEvent) {
		this.startEvent = startEvent;
	}

	public void setStartReading(LocalDateTime startReading) {
		this.startReading = startReading;
	}

	public void setEnableNotifications(Boolean enableNotifications) {
		this.enableNotifications = enableNotifications;
	}

	public void setNotificationSentOn(LocalDateTime notificationSentOn) {
		this.notificationSentOn = notificationSentOn;
	}

	public void setDisplayPrefix(String displayPrefix) {
		this.displayPrefix = displayPrefix;
	}

	public void setFinishedOn(LocalDateTime finishedOn) {
		this.finishedOn = finishedOn;
	}

	public void setLockedBy(Long lockedBy) {
		this.lockedBy = lockedBy;
	}

	public void setStartedOn(LocalDateTime startedOn) {
		this.startedOn = startedOn;
	}

	public void setWorkflowXml(String workflowXml) {
		this.workflowXml = workflowXml;
	}

	public void setEnableEmails(Boolean enableEmails) {
		this.enableEmails = enableEmails;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public void setNotificationStage(String notificationStage) {
		this.notificationStage = notificationStage;
	}

	public void setNotificationProgress(String notificationProgress) {
		this.notificationProgress = notificationProgress;
	}

	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	public void setWidgetImageContentId(Long widgetImageContentId) {
		this.widgetImageContentId = widgetImageContentId;
	}

	public void setLayoutPresetName(String layoutPresetName) {
		this.layoutPresetName = layoutPresetName;
	}

	public void setWidgetActionButtonPrompt(String widgetActionButtonPrompt) {
		this.widgetActionButtonPrompt = widgetActionButtonPrompt;
	}
	
	public void setFolder(ConsultFolder f) {
		this.folder = f;
	}
 

}
