package com.acmeflix.controller;

import com.acmeflix.base.BaseMapper;
import com.acmeflix.domain.Account;
import com.acmeflix.mapper.AccountMapper;
import com.acmeflix.service.AccountService;
import com.acmeflix.service.BaseService;
import com.acmeflix.transfer.ApiResponse;
import com.acmeflix.transfer.resource.AccountResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController extends AbstractController<Account, AccountResource> {
	private final AccountService accountService;
	private final AccountMapper accountMapper;

	@Override
	public BaseService<Account, Long> getBaseService() {
		return accountService;
	}

	@Override
	public BaseMapper<Account, AccountResource> getMapper() {
		return accountMapper;
	}

	@GetMapping(params = {"email"})
	public ResponseEntity<ApiResponse<AccountResource>> findByEmail(@RequestParam String email) {
		return ResponseEntity.ok(
				ApiResponse.<AccountResource>builder().data(getMapper().toResource(accountService.findByEmail(email)))
						.build());
	}
}
