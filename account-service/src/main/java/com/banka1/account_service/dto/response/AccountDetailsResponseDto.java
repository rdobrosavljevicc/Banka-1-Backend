package com.banka1.account_service.dto.response;

import com.banka1.account_service.domain.Account;
import com.banka1.account_service.domain.CheckingAccount;
import com.banka1.account_service.domain.Company;
import com.banka1.account_service.domain.FxAccount;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountDetailsResponseDto {
    private String nazivRacuna;
    private String brojRacuna;
    private Long vlasnik;
    private String tip;
    private BigDecimal raspolozivoStanje;
    private BigDecimal rezervisanaSredstva;
    private BigDecimal stanjeRacuna;
    private String nazivFirme;

    private String currency;
    private BigDecimal dailyLimit;
    private BigDecimal monthlyLimit;
    private BigDecimal dailySpending;
    private BigDecimal monthlySpending;
    private LocalDateTime creationDate;
    private LocalDate expirationDate;
    private String status;
    private String accountCategory;
    private String accountType;
    private String subtype;

    private String companyRegistrationNumber;
    private String companyTaxId;
    private String companyActivityCode;
    private String companyAddress;
    private Long companyOwnerId;

    private List<CardResponseDto> cards = List.of();

    public AccountDetailsResponseDto(Account account) {
        this.nazivRacuna = account.getNazivRacuna();
        this.brojRacuna = account.getBrojRacuna();
        this.vlasnik = account.getVlasnik();
        this.raspolozivoStanje = account.getRaspolozivoStanje();
        this.stanjeRacuna = account.getStanje();
        this.currency = account.getCurrency() != null ? account.getCurrency().getOznaka().name() : null;
        this.dailyLimit = account.getDnevniLimit();
        this.monthlyLimit = account.getMesecniLimit();
        this.dailySpending = account.getDnevnaPotrosnja();
        this.monthlySpending = account.getMesecnaPotrosnja();
        this.creationDate = account.getDatumIVremeKreiranja();
        this.expirationDate = account.getDatumIsteka();
        this.status = account.getStatus() != null ? account.getStatus().name() : null;

        if (account.getStanje() != null && account.getRaspolozivoStanje() != null) {
            this.rezervisanaSredstva = account.getStanje().subtract(account.getRaspolozivoStanje());
        }

        if (account instanceof CheckingAccount ca) {
            this.tip = "tekuci";
            this.accountCategory = "CHECKING";
            this.accountType = ca.getAccountConcrete().getAccountOwnershipType().name();
            this.subtype = ca.getAccountConcrete().name();
        } else if (account instanceof FxAccount fa) {
            this.tip = "devizni";
            this.accountCategory = "FOREIGN_CURRENCY";
            this.accountType = fa.getAccountOwnershipType().name();
            this.subtype = null;
        }

        Company company = account.getCompany();
        if (company != null) {
            this.nazivFirme = company.getNaziv();
            this.companyRegistrationNumber = company.getMaticni_broj();
            this.companyTaxId = company.getPoreski_broj();
            this.companyActivityCode = company.getSifraDelatnosti() != null ? company.getSifraDelatnosti().getSifra() : null;
            this.companyAddress = company.getAdresa();
            this.companyOwnerId = company.getVlasnik();
        }
    }
}
