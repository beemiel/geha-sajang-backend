package com.incense.gehasajang.controller;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.terms.Terms;
import com.incense.gehasajang.domain.terms.TermsRepository;
import com.incense.gehasajang.model.dto.terms.TermsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/terms")
@RequiredArgsConstructor
public class TermsController {

    private final TermsRepository termsRepository;

    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<TermsDto>> list() {
        return ResponseEntity.ok(toTermsDtos(termsRepository.findAll()));
    }

    private List<TermsDto> toTermsDtos(List<Terms> termsList) {
        return termsList.stream()
                .map(terms -> mapper.map(terms, TermsDto.class))
                .collect(Collectors.toList());
    }

}
