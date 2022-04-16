package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {

	public PurchaseDAO() {
	}

	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,to_date(sysdate,'YYYY-MM-DD HH24:MI:SS'),?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());

		int i = stmt.executeUpdate();
		System.out.println("1번 insert 유뮤 : " + i + " 개 행이 만들어졌습니다.");

		con.close();
		
	}

	
	/*
	 * public ProductVO getPurchase(int prodNo) throws Exception {
	 * 
	 * Connection con = DBUtil.getConnection();
	 * 
	 * String sql = "SELECT * FROM product WHERE prod_no=?";
	 * 
	 * PreparedStatement stmt = con.prepareStatement(sql); stmt.setInt(1, prodNo);
	 * 
	 * ResultSet rs = stmt.executeQuery();
	 * 
	 * ProductVO productVO = null; while (rs.next()) { productVO = new ProductVO();
	 * productVO.setProdNo(rs.getInt("PROD_NO"));
	 * productVO.setProdName(rs.getString("PROD_NAME"));
	 * productVO.setProdDetail(rs.getString("PROD_DETAIL"));
	 * productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
	 * productVO.setPrice(rs.getInt("PRICE"));
	 * productVO.setFileName(rs.getString("IMAGE_FILE"));
	 * productVO.setRegDate(rs.getDate("REG_DATE")); }
	 * 
	 * con.close();
	 * 
	 * return productVO; }
	 */
	  
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where USER_ID='" + searchVO.getSearchKeyword() + "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where USER_NAME='" + searchVO.getSearchKeyword() + "'";
			}
		}
		sql += " order by USER_ID";

		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO vo = new PurchaseVO();
				vo.setPurchaseId(rs.getString("USER_ID"));
				vo.setPurchaseName(rs.getString("USER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setRole(rs.getString("ROLE"));
				vo.setSsn(rs.getString("SSN"));
				vo.setPhone(rs.getString("CELL_PHONE"));
				vo.setAddr(rs.getString("ADDR"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : " + list.size());
		map.put("list", list);
		System.out.println("map().size() : " + map.size());

		con.close();

		return map;
	}

	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "update USERS set USER_NAME=?,CELL_PHONE=?,ADDR=?,EMAIL=? where USER_ID=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPurchaseName());
		stmt.setString(2, purchaseVO.getPhone());
		stmt.setString(3, purchaseVO.getAddr());
		stmt.setString(4, purchaseVO.getEmail());
		stmt.setString(5, purchaseVO.getPurchaseId());
		stmt.executeUpdate();

		con.close();
	}
	 
	  
	 
}
