package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		System.out.println("°¡³ª´Ù");
		System.out.println(tranNo);
		/*
		PurchaseService service=new PurchaseServiceImpl();
		PurchaseVO purchaseVO=service.getPurchase(tranNo);
		
		request.setAttribute("purchaseVO", purchaseVO);
		*/
		
		
		String userId=request.getParameter("userId");
		
		UserService userservice = new UserServiceImpl();
		UserVO uservo = userservice.getUser(userId);
		
		PurchaseVO purchaseVO=new PurchaseVO();
		purchaseVO.setTranNo(tranNo );
		purchaseVO.setBuyer(uservo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyAddr(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		
		PurchaseService service=new PurchaseServiceImpl();
		service.updatePurchase(purchaseVO);
//		
//		HttpSession session=request.getSession();
//		int sessionNO=((PurchaseVO)session.getAttribute("purchase")).getProdNo();
//	
//		if(sessionNO==prodNo){
//			session.setAttribute("purchase", purchaseVO);
//		}
		
		return "redirect:/updatePurchaseView.do?tranNo="+tranNo;
	}

}
