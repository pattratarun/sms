package com.pat.sms.search;

import static com.pat.sms.util.StringConstants.COLON;
import static com.pat.sms.util.StringConstants.COMMA;
import static com.pat.sms.util.StringConstants.DOUBLE_QUOTES;
import static com.pat.sms.util.StringConstants.EMPTY;
import static com.pat.sms.util.StringConstants.SEARCH_QUERY_PATTERN;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public final class SearchUtil {

	private SearchUtil() {
	}

	public static List<Sort.Order> getOrders(List<String> sorts, String defaultProp) {
		if (sorts.isEmpty())
			return Arrays.asList(new Sort.Order(DESC, defaultProp));
		List<Sort.Order> orders = new ArrayList<Sort.Order>(sorts.size());
		for (String sort : sorts) {
			String[] split = sort.split(COLON);
			orders.add(new Sort.Order(split.length > 1 ? ASC : DESC, split[0]));
		}
		return orders;
	}

	public static <T> Specification<T> createSpec(String query) {
		SearchSpecificationBuilder<T> builder = new SearchSpecificationBuilder<T>();
		for (Matcher matcher = SEARCH_QUERY_PATTERN.matcher(query + COMMA); matcher.find();)
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4),
					matcher.group(5).replaceAll(DOUBLE_QUOTES, EMPTY));
		return builder.build();
	}

	public static <T, R> SearchResDto prepareResponseForSearch(Page<T> page, Function<T, R> mapper) {
		SearchResDto response = new SearchResDto();
		response.setResult(page.stream().map(mapper).collect(toList()));
		response.setPageIndex(page.getNumber());
		response.setTotalPages(page.getTotalPages());
		response.setTotalRecords(page.getTotalElements());
		return response;
	}

	public static <T> T createCopyObject(Object src, Supplier<T> supplier) {
		T dest = supplier.get();
		copyProperties(src, dest);
		return dest;
	}
}