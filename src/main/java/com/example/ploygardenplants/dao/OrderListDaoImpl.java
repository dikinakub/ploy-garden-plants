package com.example.ploygardenplants.dao;

import com.example.ploygardenplants.enums.SortType;
import com.example.ploygardenplants.model.SearchModel;
import com.example.ploygardenplants.model.SearchOrderListMapper;
import com.example.ploygardenplants.model.SearchOrderListModel;
import com.example.ploygardenplants.model.SortModel;
import com.example.ploygardenplants.response.DataTableResponse;
import com.example.ploygardenplants.service.LoggerService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Optional;

@Repository("OrderListDao")
public class OrderListDaoImpl {

    protected static final String AND = " AND ";
    protected static final String OR = " OR ";

    @Autowired
    protected LoggerService loggerService;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String reference_no = "reference_no";
    private final String customer_name = "customer_name";
    private final String address_id = "address_id";
    private final String purchase_price = "purchase_price";
    private final String selling_price = "selling_price";
    private final String shipping_price = "shipping_price";
    private final String discount_price = "discount_price";
    private final String deposit = "deposit";
    private final String amount = "amount";
    private final String status_code = "status_code";
    private final String status_desc = "status_desc";
    private final String create_datetime = "create_datetime";

    private final Map<String, String> SortField = Map.ofEntries(
            Map.entry(reference_no, "reference_no"),
            Map.entry(customer_name, "customer_name"),
            Map.entry(address_id, "address_id"),
            Map.entry(purchase_price, "purchase_price"),
            Map.entry(selling_price, "selling_price"),
            Map.entry(shipping_price, "shipping_price"),
            Map.entry(discount_price, "discount_price"),
            Map.entry(deposit, "deposit"),
            Map.entry(amount, "amount"),
            Map.entry(status_code, "status_code"),
            Map.entry(status_desc, "status_desc"),
            Map.entry(create_datetime, "create_datetime")
    );

    public DataTableResponse findDetailByCriteria(List<SearchModel> listSearch, List<SortModel> listSort, int page, int perPage) {
        StringBuilder query = new StringBuilder();
        List<SearchOrderListModel> resultList;

        try {
            query.append("SELECT * ");
            query.append("FROM order_list_view ");

            Map<String, Object> criteria = convertSearchToMap(listSearch);
            MapSqlParameterSource params = new MapSqlParameterSource();
            query.append(buildWhereClause(criteria, params));
            query.append(buildSortClause(listSort, SortField));

            String sql = query.toString();

            String sql_page = String.format("%s LIMIT %d OFFSET %d ", sql, perPage, ((page * perPage) - perPage));
            String sql_count = String.format("SELECT COUNT(*) FROM (%s) T", sql);

            resultList = namedParameterJdbcTemplate.query(sql_page, params, new SearchOrderListMapper());
//            List<Integer> totals = namedParameterJdbcTemplate.queryForList(sql_count, params, Integer.class);

//            Integer total = totals.get(0);
            Integer total = 0;

            return DataTableResponse.builder().page(page).pageSize(perPage).totalItem(resultList.size())
                    .totalRecord(Long.valueOf(total)).items(resultList).buildWithNo();

        } catch (Exception e) {
            loggerService.printStackTrace("SYSTEM", e.getClass().getName(), e);
            throw e;
        }
    }

    private String buildWhereClause(Map<String, Object> criteria, MapSqlParameterSource sqlParameterSource) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(" WHERE 1=1 ");

//        if (criteria.containsKey(reference_no) && dateUtil.isValidDateFormat((String) criteria.get(paymentDateFrom), DateUtil.ISO_8601_DATE)) {
//            whereClause.append(AND + " \"PMVC_PAYMENT_DATE\" >= DATE(:" + paymentDateFrom + ") ");
//            addFieldValueToMapSqlParameterSource(paymentDateFrom, criteria.get(paymentDateFrom), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(paymentDateTo) && dateUtil.isValidDateFormat((String) criteria.get(paymentDateTo), DateUtil.ISO_8601_DATE)) {
//            whereClause.append(AND + " \"PMVC_PAYMENT_DATE\" <= DATE(:" + paymentDateTo + ") ");
//            addFieldValueToMapSqlParameterSource(paymentDateTo, criteria.get(paymentDateTo), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(dueDateFrom) && dateUtil.isValidDateFormat((String) criteria.get(dueDateFrom), DateUtil.ISO_8601_DATE)) {
//            whereClause.append(AND + " \"IVPT_DOCUMENT_DUE_DATE\" >= DATE(:" + dueDateFrom + ") ");
//            addFieldValueToMapSqlParameterSource(dueDateFrom, criteria.get(dueDateFrom), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(dueDateTo) && dateUtil.isValidDateFormat((String) criteria.get(dueDateTo), DateUtil.ISO_8601_DATE)) {
//            whereClause.append(AND + " \"IVPT_DOCUMENT_DUE_DATE\" <= DATE(:" + dueDateTo + ") ");
//            addFieldValueToMapSqlParameterSource(dueDateTo, criteria.get(dueDateTo), sqlParameterSource);
//        }
        if (criteria.containsKey(reference_no)) {
            criteria.computeIfPresent(reference_no, (key, val) -> "%" + val + "%");
            whereClause.append(AND + " (reference_no) LIKE (:" + reference_no + ") ");
            sqlParameterSource.addValue(reference_no, criteria.get(reference_no));
        }

//        if (criteria.containsKey(businessPlace)) {
//            criteria.computeIfPresent(businessPlace, (key, val) -> "%" + val + "%");
//            whereClause.append(AND + " upper(\"BC_SUPPLIER_CODE\") LIKE upper(:" + businessPlace + ") ");
//        }
//        if (criteria.containsKey(productCodeMul)) {
//            criteria.computeIfPresent(productCodeMul, (key, val) -> ((String) val).toString());
//            whereClause.append(AND + " (\"IVPD_PAYMENT_METHOD\") IN (:" + productCodeMul + ") ");
//            addFieldValueToMapSqlParameterSource(productCodeMul, criteria.get(productCodeMul), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(beneficiaryBank)) {
//            criteria.computeIfPresent(beneficiaryBank, (key, val) -> "%" + val + "%");
//            whereClause.append(AND + " (\"THAB_BANK_SHORT_NAME\") LIKE (:" + beneficiaryBank + ") ");
//            addFieldValueToMapSqlParameterSource(beneficiaryBank, criteria.get(beneficiaryBank), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(beneficiaryName)) {
//            criteria.computeIfPresent(beneficiaryName, (key, val) -> "%" + val + "%");
//            whereClause.append(AND + " upper(\"IVPD_RECEIVING_ACCOUNT_NAME\") LIKE upper(:" + beneficiaryName + ") ");
//            addFieldValueToMapSqlParameterSource(beneficiaryName, criteria.get(beneficiaryName), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(beneficiaryAccountNo)) {
//            criteria.computeIfPresent(beneficiaryAccountNo, (key, val) -> "%" + val + "%");
//            whereClause.append(AND + " upper(\"IVPD_RECEIVING_ACCOUNT_NO\") LIKE upper(:" + beneficiaryAccountNo + ") ");
//            addFieldValueToMapSqlParameterSource(beneficiaryAccountNo, criteria.get(beneficiaryAccountNo), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(status)) {
//            criteria.computeIfPresent(status, (key, val) -> ((String) val).toUpperCase());
//            whereClause.append(AND + " (\"IVPD_STATUS_CODE\") IN (:" + status + ") ");
//            addFieldValueToMapSqlParameterSource(status, criteria.get(status), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(buyerCompCode)) {
//            criteria.computeIfPresent(buyerCompCode, (key, val) -> ((String) val).toUpperCase());
//            whereClause.append(AND + " (\"IVPT_BUYER_COMP_CODE\") IN (:" + buyerCompCode + ") ");
//            addFieldValueToMapSqlParameterSource(buyerCompCode, criteria.get(buyerCompCode), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(buyerBusinessPlace)) {
//            criteria.computeIfPresent(buyerBusinessPlace, (key, val) -> ((String) val).toUpperCase());
//            whereClause.append(AND + " (\"IVPT_BC_BUYER_BRANCH_CODE\") IN (:" + buyerBusinessPlace + ") ");
//            addFieldValueToMapSqlParameterSource(buyerBusinessPlace, criteria.get(buyerBusinessPlace), sqlParameterSource);
//        }
//
//        if (criteria.containsKey(bcSupplierCode)) {
//            criteria.computeIfPresent(bcSupplierCode, (key, val) -> ((String) val).toUpperCase());
//            whereClause.append(AND + " upper(\"IVPT_BC_SUPPLIER_CODE\") IN (:" + bcSupplierCode + ") ");
//            sqlParameterSource.addValue((bcSupplierCode, criteria.get(bcSupplierCode), sqlParameterSource);
//        }
        return whereClause.toString();
    }

    private String buildSortClause(List<SortModel> listSort, Map<String, String> availableSortField) {
        String sortClause = Optional.ofNullable(listSort)
                .orElse(new ArrayList<>())
                .stream()
                .filter(it -> availableSortField.containsKey(it.getField()))
                .map(it -> {
                    return String.format("%s %s NULLS %s",
                            availableSortField.get(it.getField()),
                            it.getOrder(),
                            SortType.ASC.equals(it.getOrderSafe()) ? "FIRST" : "LAST");
                }
                ).collect(Collectors.joining(", "));

        if (sortClause != null && !sortClause.isEmpty()) {
            return " ORDER BY " + sortClause;
        }
        return "";
    }

    protected Map<String, Object> convertSearchToMap(List<SearchModel> listSearch) {
        Map<String, Object> map = new HashMap<>();
        if (listSearch != null) {
            Set<String> keySearch = Sets.newHashSet(listSearch.stream().map(SearchModel::getField).collect(Collectors.toList()));
            for (SearchModel s : listSearch) {
                Integer specialIdx = s.getField().indexOf("$");
                if (specialIdx > -1) {
                    String splitKey = s.getField().substring(specialIdx + 1);
                    String newKey = s.getField().substring(0, specialIdx);
                    if (keySearch.contains(newKey)) {
                        continue; // override key
                    }
                    List<String> newValue = Arrays.asList(s.getValue().split(splitKey));
                    map.put(newKey, newValue);
                } else {
                    map.put(s.getField(), s.getValue().replace("_", "\\_"));
                }
            }
        }
        return map;
    }
}
