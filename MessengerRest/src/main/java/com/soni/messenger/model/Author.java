package com.soni.messenger.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter @Setter @NoArgsConstructor
@Entity 
@Table(name="AUTHORS_T")
public class Author extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AUTHOR_ID")
	private Integer authorId;
	
	@Id
	@Column(name="AUTHOR_NAME")
	private String authorName;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@JsonIgnore
	@OneToMany(mappedBy="author")
	private Set<Message> messages = new HashSet<>();
}
