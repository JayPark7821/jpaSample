package com.example.giggaldemo.controller;

import com.example.giggaldemo.dto.MemberDto;
import com.example.giggaldemo.entity.Charge;
import com.example.giggaldemo.entity.Member;
import com.example.giggaldemo.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class SampleControllerTest {

    @Autowired
    WebApplicationContext wac;
    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mvc;

    @Autowired
    MemberRepository memberRepository;

    MvcResult requestMvc(String bodyContent, String url) throws Exception {

        return this.mvc.perform(post(url)
                .content(bodyContent)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();
    }

    @BeforeEach
    void getAccessToken() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .dispatchOptions(true);
        this.mvc = builder.build();
    }

    @Test
    void app_health_check () throws Exception {

        MvcResult mvcResult = this.mvc.perform(get("/check"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).contains("ok");
    }

    @Test
    void 기깔없이_맴버등록_Test () throws Exception {

        //given
        MemberDto memberA = MemberDto.builder()
                .name("memberA")
                .charge(Charge.BACKEND)
                .build();

        //when
        MvcResult result = requestMvc(objectMapper.writeValueAsString(memberA), "/member");

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).contains("ok");
        assertThat(result.getResponse().getContentAsString()).contains("inserted");

        List<Member> all = memberRepository.findAll();
        assertThat(all).extracting("name").containsExactly("memberA");
    }

    @Test
    void 기깔없이_맴버수정_Test () throws Exception {
        //given
        Member member = Member.builder()
                .name("memberA")
                .charge(Charge.BACKEND)
                .build();
        Member savedMember = memberRepository.save(member);

        //when
        MemberDto memberA = MemberDto.builder()
                .id(savedMember.getId())
                .name("memberB")
                .charge(Charge.FRONTEND)
                .build();
        MvcResult result = requestMvc(objectMapper.writeValueAsString(memberA), "/member");

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).contains("ok");
        assertThat(result.getResponse().getContentAsString()).contains("updated");

        Member selectedMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(selectedMember.getName()).isEqualTo("memberB");
        assertThat(selectedMember.getCharge()).isEqualTo(Charge.FRONTEND);
    }


}