package com.NBE_4_5_2.Team5.domain.user.user.repository

import com.NBE_4_5_2.Team5.domain.user.user.entity.RefreshToken
import com.NBE_4_5_2.Team5.global.config.BaseTestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
@BaseTestConfig
internal open class RedisContainersKotlinTest {

    @Autowired
    lateinit var redisRepository: RedisRepository

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Test
    @DisplayName("redis : TestContainers 정상 동작 확인")
    fun test1() {
        // GIVEN: RedisTemplate을 통해 Redis 서버와 연결되어 있음
        // WHEN: ping 명령어를 실행하면
        val pingResponse = redisTemplate.execute { connection -> connection.ping() }
        // THEN: "PONG" 응답을 받아야 한다.
        assertThat(pingResponse).isEqualTo("PONG")
    }

    @Test
    @DisplayName("redis : 동일한 ConnectionFactory를 사용하는지 확인")
    fun test2() {
        // GIVEN: StringRedisTemplate과 RedisTemplate 모두 동일한 ConnectionFactory를 사용함
        // THEN: 두 템플릿의 connectionFactory가 동일해야 한다.
        assertThat(redisTemplate.connectionFactory)
            .isSameAs(stringRedisTemplate.connectionFactory)
    }

    @Test
    @DisplayName("redis : StringRedisTemplate : 저장 및 조회 테스트")
    fun test3() {
        // GIVEN: key와 value를 정의
        val key = "test-key"
        val value = "test-value"

        // WHEN: StringRedisTemplate을 통해 key에 value를 저장하고
        stringRedisTemplate.opsForValue()[key] = value
        // THEN: 해당 key로 조회 시 저장된 value와 동일해야 한다.
        val storedValue = stringRedisTemplate.opsForValue()[key]
        assertThat(storedValue).isEqualTo(value)
    }

    @Test
    @DisplayName("redis : RedisTemplate : 저장 및 조회 테스트")
    fun test4() {
        // GIVEN: RedisTemplate으로 사용할 key와 value
        val key = "redis-template-key"
        val value = "redis-template-value"

        // WHEN: RedisTemplate에 key, value를 저장하고
        redisTemplate.opsForValue()[key] = value
        // THEN: 해당 key로 조회 시 동일한 value가 반환되어야 한다.
        val storedValue = redisTemplate.opsForValue()[key]
        assertThat(storedValue).isEqualTo(value)
    }

    @Test
    @DisplayName("redis : RedisRepository : RefreshToken 저장 조회 테스트")
    fun test5() {
        // GIVEN: RefreshToken 객체 생성
        val userId = "user123"
        val refreshToken = "refresh-token-abc"
        val expiration = 3600L
        val token = RefreshToken.builder()
            .userId(userId)
            .refreshToken(refreshToken)
            .expiration(expiration)
            .build()

        // WHEN: RedisRepository에 저장 후 userId로 조회
        redisRepository.save(token)
        val foundToken = redisRepository.findById(userId)

        // THEN: 조회된 토큰이 존재하며, 각 필드 값이 일치해야 한다.
        assertThat(foundToken).isPresent
        with(foundToken.get()) {
            assertThat(userId).isEqualTo(userId)
            assertThat(refreshToken).isEqualTo(refreshToken)
            assertThat(expiration).isEqualTo(expiration)
        }
    }

    @Test
    @DisplayName("redis : RedisRepository, StringRedisTemplate, RedisTemplate : 같은 Redis를 사용하는지 확인")
    fun test6() {
        // GIVEN: 공유할 key와 value를 정의
        val key = "shared-key"
        val value = "shared-value"

        // WHEN: StringRedisTemplate으로 저장 후 RedisTemplate으로 조회
        stringRedisTemplate.opsForValue()[key] = value
        val redisTemplateValue = redisTemplate.opsForValue()[key]
        // THEN: 두 템플릿 모두 동일한 value를 반환해야 한다.
        assertThat(redisTemplateValue).isEqualTo(value)
        assertThat(stringRedisTemplate.opsForValue()[key]).isEqualTo(value)
    }

    @Test
    @DisplayName("redis : redisRepository : RefreshToken 삭제 테스트")
    fun test7() {
        // GIVEN: 삭제할 RefreshToken 객체 생성 후 저장
        val userId = "userToDelete"
        val token = RefreshToken.builder()
            .userId(userId)
            .refreshToken("token-to-delete")
            .expiration(3600L)
            .build()
        redisRepository.save(token)

        // WHEN: 해당 userId로 삭제한 후 재조회
        redisRepository.deleteById(userId)
        val foundToken = redisRepository.findById(userId)
        // THEN: 조회 결과가 비어 있어야 한다.
        assertThat(foundToken).isEmpty
    }

    @Test
    @DisplayName("redis : redisRepository : RefreshToken으로 조회 테스트")
    fun test8() {
        // GIVEN: 특정 refreshToken 값과 함께 RefreshToken 객체 생성 및 저장
        val userId = "userWithRefreshToken"
        val refreshToken = "unique-refresh-token"
        val token = RefreshToken.builder()
            .userId(userId)
            .refreshToken(refreshToken)
            .expiration(3600L)
            .build()
        redisRepository.save(token)

        // WHEN: refreshToken으로 조회
        val foundToken = redisRepository.findByRefreshToken(refreshToken)
        // THEN: 조회된 토큰의 필드값이 올바른지 검증
        assertThat(foundToken).isPresent
        with(foundToken.get()) {
            assertThat(userId).isEqualTo(userId)
            assertThat(refreshToken).isEqualTo(refreshToken)
        }
    }
}
