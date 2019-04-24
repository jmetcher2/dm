package com.objective.keystone.model.event;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.ChildDomainObject;
import au.id.lagod.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.ConsultFolder;
import com.sun.istack.NotNull;

@MappedSuperclass
public class MappedEvent extends BaseDomainObject implements ChildDomainObject {

	@Id	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "event-generator")
	@GenericGenerator(
			name="event-generator",
			strategy="au.id.lagod.dm.persistence.CustomTableIDGenerator",
			parameters = {
					@Parameter(name="sp_name", value="get_event_id_nextval")
			}
		)
	@Column(name="event_id", updatable = false, nullable = false)						protected Long id;

	@TextKey
	@Size(max=255)
	@Column(name="event_short_name")				protected String shortName; 

	@ManyToOne 
	@JoinColumn(name="event_customer_id")			protected Customer customer;
	
	@ManyToOne 
	@JoinColumn(name="event_folder_id")				protected ConsultFolder folder;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="event_type")						protected EventType type;

	@Generated( value = GenerationTime.ALWAYS )
	@Column(name="status")							protected String status;

	@Column(name="event_archive_representations")	protected LocalDateTime archiveRepresentations; 
	@Column(name="event_copy_id")					protected Long copyId; 
	@Column(name="event_created_by")				protected Long createdBy; 
	@Column(name="event_created_on")				protected LocalDateTime createdOn; 
	@Column(name="event_map_id")					protected Long mapId; 
	@Column(name="event_end_event")					protected LocalDateTime endEvent; 
	@Column(name="event_xml")						protected String xml; 
	@Size(max=10)
	@Column(name="event_invite_status")				protected String inviteStatus; 
	@Size(max=10)
	@Column(name="event_live_status")				protected String liveStatus; 
	@Column(name="event_metadata")					protected String metadata; 
	@Column(name="event_modified_by")				protected Long modifiedBy; 
	@Column(name="event_modified_on")				protected LocalDateTime modifiedOn; 
	@NotNull
	@Size(max=255)
	@Column(name="event_name")						protected String name;
	@Column(name="event_questionnaire_id")			protected Long questionnaireId; 
	@Size(max=12)
	@Column(name="event_privacy_status")			protected String privacyStatus; 
	@Column(name="event_start_event")				protected LocalDateTime startEvent; 
	@Column(name="event_start_reading")				protected LocalDateTime startReading; 
	@Column(name="event_enable_notifications")		protected Boolean enableNotifications; 
	@Column(name="event_notification_sent_on")		protected LocalDateTime notificationSentOn; 
	@Column(name="event_send_reminder_on")			protected LocalDateTime sendReminderOn; 
	@Size(max=255)
	@Column(name="event_display_prefix")			protected String displayPrefix; 
	@Column(name="event_finished_on")				protected LocalDateTime finishedOn; 
	@Column(name="event_locked_by")					protected Long lockedBy; 
	@Column(name="event_questionnaire_version_id")	protected Long questionnaireVersionId; 
	@Column(name="event_started_on")				protected LocalDateTime startedOn; 
	@Size(max=10)
	@Column(name="event_version_number")			protected String versionNumber; 
	@Column(name="event_workflow_xml")				protected String workflowXml; 
	@Column(name="event_representation_workflow_id") protected Long representationWorkflowId; 
	@Column(name="event_enable_emails")				protected Boolean enableEmails; 
	@Column(name="event_importance")				protected Integer importance; 
	@Size(max=10)
	@Column(name="event_standalone_status")			protected String standaloneStatus;
	@Column(name="event_template")					protected Boolean template; 
	@Size(max=5)
	@Column(name="event_creation_language")			protected String creationLanguage; 
	@Column(name="event_owner")						protected Long owner; 
	@Column(name="event_owner_consult_group")		protected Long ownerConsultGroup; 
	@Column(name="event_owner_consult_person")		protected Long ownerConsultPerson; 
	@Column(name="event_owner_group")				protected Long ownerGroup; 
	@Size(max=20)
	@Column(name="event_submission_status")			protected String submissionStatus; 
	@Column(name="event_target")					protected Integer target; 
	@Column(name="event_response_threshold")		protected Integer responseThreshold; 
	@Size(max=20)
	@Column(name="event_notification_stage")		protected String notificationStage; 
	@Column(name="event_is_shredded")				protected Boolean isShredded; 
	@Column(name="event_shredding_complete")		protected Boolean shreddingComplete; 
	@Column(name="event_document_id")				protected Long documentId; 
	@Size(max=10)
	@Column(name="event_notification_progress")		protected String notificationProgress; 
	@NotNull
	@Column(name="event_etag")						protected String eTag;
	@Column(name="event_event_branding_id")			protected Long eventBrandingId; 
	@Column(name="event_widget_image_content_id")	protected Long widgetImageContentId; 
	@Size(max=255)
	@Column(name="event_widget_image_name")			protected String widgetImageName; 
	@Size(max=255)
	@Column(name="event_layout_preset_name")		protected String layoutPresetName; 
	@Size(max=255)
	@Column(name="event_widget_action_button_prompt") protected String widgetActionButtonPrompt;
	
	public MappedEvent() {}
	
	@Override
	public Customer getParent() {
		return getCustomer();
	}
	
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getShortName() {
		return shortName;
	}
	
	public EventType getType() {
		return type;
	}
}
