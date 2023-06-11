package kr.inhatc.pip.basic.service;

import kr.inhatc.pip.basic.entity.Basic;
import kr.inhatc.pip.basic.repository.BasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicService {

    private final BasicRepository basicRepository;

    public Long join(Basic basic){
        validateDuplicateCheck(basic);      // 중복 회원 검증
        basicRepository.save(basic);
        return basic.getId();
    }

    private void validateDuplicateCheck(Basic basic) {
        basicRepository.findByName(basic.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 나이가 동일한 사람들을 찾는다.
    public List<Basic> findSameAge(int age) {
        return basicRepository.findByAge(age);
    }

    public Optional<Basic> findByNameAge(String name, int age) {
        return basicRepository.findByNameAndAge(name, age);
    }

    public List<Basic> findBasic() {
        return basicRepository.findAll();
    }

    public Optional<Basic> findOne(Long basicId) {
        return basicRepository.findById(basicId);
    }
}
