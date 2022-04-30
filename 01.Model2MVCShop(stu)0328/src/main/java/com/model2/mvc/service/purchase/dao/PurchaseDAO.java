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
import com.model2.mvc.service.user.vo.UserVO;

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

	
	
	public PurchaseVO getPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE TRAN_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO purchaseVO = null;
		while (rs.next()) {

			purchaseVO = new PurchaseVO();

			ProductVO product = new ProductVO();
			product.setProdNo(rs.getInt("PROD_NO"));

			UserVO user = new UserVO();
			user.setUserId(rs.getString("buyer_id"));

			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(product);
			purchaseVO.setBuyer(user);
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));

		}

		con.close();

		return purchaseVO;
	}
	  
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE BUYER_ID ='"+userId+"'";
			
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
				UserVO user = new UserVO();
				user.setUserId(rs.getString("buyer_id"));
				ProductVO product = new ProductVO();
				product.setProdNo(rs.getInt("PROD_NO"));
				vo.setBuyer(user);
				vo.setTranNo(rs.getInt("TRAN_NO"));
				vo.setPurchaseProd(product);
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setDivyDate(rs.getString("DLVY_DATE"));

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

		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		
		int i = stmt.executeUpdate();
		System.out.println("1번 insert 유뮤 : " + i + " 개 행이 수정되었습니다.");

		con.close();
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET tran_status_code = ? WHERE tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());
		
		int i = stmt.executeUpdate();
		System.out.println("TranCode 1번 insert 유뮤 : " + i + " 개 행이 수정되었습니다.");
		
		con.close();

	}
	 
	  
	
}
