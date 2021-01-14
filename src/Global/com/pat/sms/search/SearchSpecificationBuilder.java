package com.pat.sms.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecificationBuilder<T> {
	private final List<SearchCriteria> params;

	public SearchSpecificationBuilder() {
		params = new ArrayList<>();
	}

	public SearchSpecificationBuilder<T> with(String clause, String type, String key, String operation, String value) {
		params.add(new SearchCriteria(clause, type, key, operation, value));
		return this;
	}

	public Specification<T> build() {
		if (params.isEmpty())
			return null;
		Iterator<SearchCriteria> iterator = params.iterator();
		SearchCriteria criteria = iterator.next();
		Specification<T> result = Specification.where(new SearchSpecification<T>(criteria));
		while (iterator.hasNext()) {
			criteria = iterator.next();
			result = criteria.isClauseAnd() ? result.and(new SearchSpecification<T>(criteria))
					: result.or(new SearchSpecification<T>(criteria));
		}
		return result;
	}
}
