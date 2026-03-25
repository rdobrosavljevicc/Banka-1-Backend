package com.banka1.account_service.service;

import com.banka1.account_service.dto.request.CheckingDto;
import com.banka1.account_service.dto.request.FxDto;
import com.banka1.account_service.dto.request.UpdateCardDto;
import com.banka1.account_service.dto.response.AccountSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Servis koji implementira poslovnu logiku dostupnu zaposlenima banke.
 * Pokriva kreiranje racuna, pretragu racuna i upravljanje karticama.
 */
public interface EmployeeService {

    /**
     * Kreira novi devizni (FX) racun za zadatog klijenta.
     * Nakon uspesnog cuvanja salje email notifikaciju i, ako je zatrazeno,
     * event ka Card Service-u za kreiranje kartice.
     *
     * @param jwt   JWT token zaposlenog koji inicira kreiranje
     * @param fxDto podaci o FX racunu (valuta, tip vlasnistva, inicijalni saldo, itd.)
     * @return poruka o uspesnosti kreiranja
     */
    String createFxAccount(Jwt jwt, FxDto fxDto);

    /**
     * Kreira novi tekuci (RSD) racun za zadatog klijenta.
     * Nakon uspesnog cuvanja salje email notifikaciju i, ako je zatrazeno,
     * event ka Card Service-u za kreiranje kartice.
     *
     * @param jwt         JWT token zaposlenog koji inicira kreiranje
     * @param checkingDto podaci o tekucem racunu (vrsta racuna, inicijalni saldo, firma, itd.)
     * @return poruka o uspesnosti kreiranja
     */
    String createCheckingAccount(Jwt jwt, CheckingDto checkingDto);

    /**
     * Pretrazuje sve racune u sistemu sa opcionalnim filterima.
     * Rezultati su sortirani po prezimenu pa imenu vlasnika.
     *
     * @param jwt                   JWT token zaposlenog
     * @param imeVlasnikaRacuna     opcioni filter po imenu vlasnika (parcijalno poklapanje)
     * @param prezimeVlasnikaRacuna opcioni filter po prezimenu vlasnika (parcijalno poklapanje)
     * @param accountNumber         opcioni filter po broju racuna (parcijalno poklapanje)
     * @param page                  broj stranice (0-indeksiran)
     * @param size                  velicina stranice
     * @return stranica {@link AccountSearchResponseDto} koji odgovaraju filterima
     */
    Page<AccountSearchResponseDto> searchAllAccounts(Jwt jwt, String imeVlasnikaRacuna, String prezimeVlasnikaRacuna, String accountNumber, int page, int size);

    /**
     * Azurira status kartice vezane za racun.
     *
     * @param jwt           JWT token zaposlenog
     * @param id            ID kartice
     * @param updateCardDto zeljeni novi status kartice
     * @return poruka o uspesnosti azuriranja
     */
    String updateCard(Jwt jwt, Long id, UpdateCardDto updateCardDto);
}
