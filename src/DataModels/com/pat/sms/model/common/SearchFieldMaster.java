package com.pat.sms.model.common;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "searchField_master",
        indexes = @Index(name = "idx_searchScreen", columnList = "search_screen", unique = true)
)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class SearchFieldMaster {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
    @Column(name="search_screen", length = 200)
    private String searchScreen;
    
    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String searchfields;

    @Column(length = 100)
    private String env;
    
}
