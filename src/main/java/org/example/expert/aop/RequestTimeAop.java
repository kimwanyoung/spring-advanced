package org.example.expert.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
public class RequestTimeAop {

	@Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment*(..))")
	private void deleteComment() {
	}

	@Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole*(..))")
	private void changeUserRole() {
	}

	@AfterReturning(value = "deleteComment() || changeUserRole()", returning = "returnObj")
	public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return;
		}

		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		log.info("======= 요청 정보 =======");
		log.info("User ID: {}", request.getAttribute("userId"));
		log.info("User Email: {}", request.getAttribute("email"));
		log.info("Request Time: {}", LocalDateTime.now());
		log.info("Request URL: {}", request.getRequestURL());
		log.info("HTTP Method: {}", request.getMethod());
		log.info("Controller Method: {}", signature.getMethod().getName() + "()");
		log.info("Request Body: {}", Arrays.toString(joinPoint.getArgs()));
		log.info("Response Status: {}", response != null ? response.getStatus() : "null");
		log.info("Response Body: {}", returnObj != null ? returnObj : "No content");
	}
}