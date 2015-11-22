package com.soni.messenger.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonView;

import com.soni.messenger.json.views.Views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter @Setter @NoArgsConstructor
@Entity 
@Table(name="MESSAGES_T")
public class Message extends AuditableEntity{
	
	private static final long serialVersionUID = 6380706582447114024L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MESSAGE_ID")
	@JsonView(Views.Public.class)
	private Integer messageId;
	
	@Column(name="MESSAGE")
	@JsonView(Views.Public.class)
	private String message;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AUTHOR_NAME")
	@JsonView(Views.Internal.class)
	private Author author;
	
	@OneToMany(mappedBy="message")
	@JsonView(Views.InnerInternal.class)
	private Set<Comment> comments = new HashSet<>();
	
	@JsonView(Views.Internal.class)
	private transient List<Link> links = new ArrayList<>();
	
}
