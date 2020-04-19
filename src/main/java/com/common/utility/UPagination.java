package com.common.utility;

import java.util.List;

import javax.portlet.ResourceRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
 
/**
 * @author Rahul.Mangal
 *
 */
public class UPagination {

	
	public static Pageable getPageRequest(ResourceRequest request){
		return getPageRequest(request,null);
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static Pageable getPageRequest(ResourceRequest request, String columnName) {

		Sort sort = getSort(getDirection(request.getParameter("order[0][dir]")), columnName);
		int start = Utility.getInt(Utility.defOnEmp(request.getParameter("start"), "0"));

		int length = Utility.getInt(Utility.defOnEmp(request.getParameter("length"), "10"));
		length = length > 0 ? length : 10;
		int iPages = start / length;

		return new PageRequest(iPages, length, sort);
	}

	public static UPagingBO getPageData(Page obj) {
		return getPageData(null, obj.getTotalElements(), obj.getContent());
	}
	
	public static UPagingBO getPageData(long totalRecords, List<?> list) {
		return getPageData(null, totalRecords, list);
	}

	public static UPagingBO getPageData(String sEcho, long totalRecords, List<?> list) {
		UPagingBO pagingBO = new UPagingBO();
		pagingBO.setAaData(list);
		pagingBO.setsEcho(sEcho);
		pagingBO.setiTotalDisplayRecords((int) totalRecords);
		pagingBO.setiTotalRecords((int) totalRecords);
		return pagingBO;
	}

	/**
	 * Default sorting Column as assumed id if sortColoumnName is null or empty.
	 * 
	 * @param sortDirection
	 * @param sortColumnName
	 * @return Spring sort object
	 */
	public static Sort getSort(Direction sortDirection, String sortColumnName) {
		if (sortDirection == null) {
			sortDirection = Sort.Direction.ASC;
		}
		return new Sort(sortDirection, Utility.defOnEmp(sortColumnName, "id"));
	}

	public static Direction getDirection(String str) {
		if (Utility.isNullOrEmp(str)) {
			return Sort.Direction.DESC;
		} else {
			if ("asc".equalsIgnoreCase(str))
				return Sort.Direction.ASC;
			else
				return Sort.Direction.DESC;
		}
	}
}
