package com.pat.sms.search;

import static com.pat.sms.util.StringConstants.COMMA;
import static com.pat.sms.util.StringConstants.CONTAINS;
import static com.pat.sms.util.StringConstants.EQUALS;
import static com.pat.sms.util.StringConstants.G_T;
import static com.pat.sms.util.StringConstants.G_T_EQUALS;
import static com.pat.sms.util.StringConstants.IN;
import static com.pat.sms.util.StringConstants.LIKE_ENDS_WITH;
import static com.pat.sms.util.StringConstants.LIKE_EQUAL;
import static com.pat.sms.util.StringConstants.LIKE_PRE_POST;
import static com.pat.sms.util.StringConstants.LIKE_STARTS_WITH;
import static com.pat.sms.util.StringConstants.L_T;
import static com.pat.sms.util.StringConstants.L_T_EQUALS;
import static com.pat.sms.util.StringConstants.NOT_EQUALS;
import static java.lang.Boolean.parseBoolean;
import static java.lang.String.format;
import static java.time.Instant.parse;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = -0x2733DE2E86ED0B65L;
	private SearchCriteria criteria;

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		switch (criteria.getType()) {
		case NUM_PARAM: {
			if (criteria.getOperation().equals(G_T))
				return criteriaBuilder.gt(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(L_T))
				return criteriaBuilder.lt(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(EQUALS))
				return criteriaBuilder.equal(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(NOT_EQUALS))
				return criteriaBuilder.notEqual(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(G_T_EQUALS))
				return criteriaBuilder.ge(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(L_T_EQUALS))
				return criteriaBuilder.le(root.get(criteria.getKey()), new BigDecimal(criteria.getValue()));
			if (criteria.getOperation().equals(IN)) {
				In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
				for (String str : criteria.getValue().split(COMMA))
					in.value(new BigDecimal(str));
				return in;
			}
		}
			return null;
		case DATE_TIME_PARAM: {
			if (criteria.getOperation().equals(G_T))
				return criteriaBuilder.greaterThan(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(L_T))
				return criteriaBuilder.lessThan(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(EQUALS))
				return criteriaBuilder.equal(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(NOT_EQUALS))
				return criteriaBuilder.notEqual(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(G_T_EQUALS))
				return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(L_T_EQUALS))
				return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), parse(criteria.getValue()));
			if (criteria.getOperation().equals(IN)) {
				In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
				for (String str : criteria.getValue().split(COMMA))
					in.value(parse(str));
				return in;
			}
		}
			return null;
		case STRING_PARAM: {
			if (criteria.getOperation().equals(EQUALS))
				return criteriaBuilder.like(root.<String>get(criteria.getKey()),
						format(LIKE_EQUAL, criteria.getValue()));
			if (criteria.getOperation().equals(CONTAINS))
				return criteriaBuilder.like(root.<String>get(criteria.getKey()),
						format(LIKE_PRE_POST, criteria.getValue()));
			if (criteria.getOperation().equals(L_T))
				return criteriaBuilder.like(root.<String>get(criteria.getKey()),
						format(LIKE_STARTS_WITH, criteria.getValue()));
			if (criteria.getOperation().equals(G_T))
				return criteriaBuilder.like(root.<String>get(criteria.getKey()),
						format(LIKE_ENDS_WITH, criteria.getValue()));
			if (criteria.getOperation().equals(NOT_EQUALS))
				return criteriaBuilder.notLike(root.<String>get(criteria.getKey()),
						format(LIKE_PRE_POST, criteria.getValue()));
			if (!criteria.getOperation().equals(IN))
				return null;
			In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
			for (String str : criteria.getValue().split(COMMA))
				in.value(str);
			return in;
		}
		case BOOLEAN_PARAM:
			return criteriaBuilder.equal(root.get(criteria.getKey()), parseBoolean(criteria.getValue()));
		default:
			return null;
		}
	}
}
