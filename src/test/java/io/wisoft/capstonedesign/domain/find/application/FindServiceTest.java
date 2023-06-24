package io.wisoft.capstonedesign.service;

import io.wisoft.capstonedesign.domain.find.application.FindService;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
import io.wisoft.capstonedesign.domain.find.web.dto.CreateFindRequest;
import io.wisoft.capstonedesign.domain.find.web.dto.UpdateFindRequest;
import io.wisoft.capstonedesign.domain.user.application.UserService;
import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Tag;
import io.wisoft.capstonedesign.global.exception.service.PostNotFoundException;
import io.wisoft.capstonedesign.global.exception.service.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.wisoft.capstonedesign.setting.data.DefaultFindData.createDefaultFind;
import static io.wisoft.capstonedesign.setting.data.DefaultUserData.createDefaultUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class FindServiceTest {

    @Autowired
    FindService findService;
    @Autowired
    UserService userService;

    @Nested
    @DisplayName("게시글 작성 테스트")
    class WritePost {

        @Test
        @DisplayName("게시글 작성시 정상적으로 저장되어야 한다.")
        void write_post() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);

            // when
            Long savedId = findService.join(findRequest);

            // then
            Find find = findService.findById(savedId);
            assertEquals("패딩 찾아봅니다", find.getTitle());
        }

        @Test
        @DisplayName("회원이 아닌 사용자가 게시글 작성시 예외가 발생해야 한다.")
        void write_post_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(100L);

            // when

            // then
            assertThrows(UserNotFoundException.class, () -> findService.join(findRequest));
        }
    }

    @Nested
    @DisplayName("게시글 조회 테스트")
    class FindPost {

        @Test
        @DisplayName("게시글 단건 조회 요청시 한 게시글이 조회되어야 한다.")
        void find_single_post() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            Long savedId = findService.join(findRequest);

            // when
            Find savedFind = findService.findById(savedId);

            // then
            assertEquals("패딩 찾아봅니다", savedFind.getTitle());
            assertEquals("안 입는 패딩 기부받아요", savedFind.getText());
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 조회시 예외가 발생해야 한다.")
        void find_single_post_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            Long savedId = findService.join(findRequest);

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> findService.findById(100L));
        }

        @Test
        @DisplayName("전체 게시글 조회시 전체 게시글 목록을 반환해야 한다.")
        void find_find_posts() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest1 = createDefaultFind(userId);
            findService.join(findRequest1);

            CreateFindRequest findRequest2 = CreateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId)
                    .build();
            findService.join(findRequest2);

            // when
            List<Find> finds = findService.findFinds();

            // then
            assertEquals(2, finds.size());
            assertEquals("패딩 찾아봅니다", finds.get(0).getTitle());
        }

        @Test
        @DisplayName("전체 게시글을 페이징을 사용하여 조회시 페이지 번호와 내림차순으로 정렬된 페이지가 반환되어야 한다.")
        void find_posts_using_paging() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest1 = createDefaultFind(userId);
            findService.join(findRequest1);

            CreateFindRequest findRequest2 = CreateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId)
                    .build();
            findService.join(findRequest2);

            CreateFindRequest findRequest3 = CreateFindRequest.builder()
                    .title("후드티 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 후드티 기부받아요")
                    .tag(String.valueOf(Tag.TOP))
                    .userId(userId)
                    .build();
            findService.join(findRequest3);
            PageRequest request = PageRequest.of(0, 5, Sort.by("createdDate").descending());

            // when
            List<Find> finds = findService.findByCreatedDateDescUsingPaging(request).getContent();

            // then
            assertEquals("후드티 찾아봅니다", finds.get(0).getTitle());
        }

        @Test
        @DisplayName("전체 게시글을 태그별로 조회시 태그에 맞는 게시글만 반환되어야 한다.")
        void find_posts_by_tag() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest1 = createDefaultFind(userId);
            findService.join(findRequest1);

            CreateFindRequest findRequest2 = CreateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .userId(userId)
                    .build();
            findService.join(findRequest2);

            CreateFindRequest findRequest3 = CreateFindRequest.builder()
                    .title("후드티 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 후드티 기부받아요")
                    .tag(String.valueOf(Tag.TOP))
                    .userId(userId)
                    .build();
            findService.join(findRequest3);

            // when
            List<Find> byTag = findService.findByTag(Tag.TOP);

            // then
            assertEquals(2, byTag.size());
        }
    }

    @Nested
    @DisplayName("게시글 수정 테스트")
    class UpdatePost {

        @Test
        @DisplayName("게시글 수정시 게시글이 수정되어야 한다.")
        void update_post() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            Long savedId = findService.join(findRequest);

            UpdateFindRequest updateRequest = UpdateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .findId(userId)
                    .build();

            // when
            findService.updateAll(savedId, updateRequest);
            Find updateFind = findService.findById(savedId);

            // then
            assertEquals("바지 찾아봅니다", updateFind.getTitle());
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 수정시 예외가 발생해야 한다.")
        void update_post_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            findService.join(findRequest);

            UpdateFindRequest updateRequest = UpdateFindRequest.builder()
                    .title("바지 찾아봅니다")
                    .image("image.png")
                    .text("안 입는 바지 기부받아요")
                    .tag(String.valueOf(Tag.PANTS))
                    .findId(userId)
                    .build();

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> findService.updateAll(100L, updateRequest));
        }
    }

    @Nested
    @DisplayName("게시글 삭제 테스트")
    class DeletePost {

        @Test
        @DisplayName("게시글 삭제시 게시글이 삭제되어야 한다.")
        void delete_post() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            Long savedId = findService.join(findRequest);

            // when
            findService.deleteFind(savedId);

            // then
            assertThrows(PostNotFoundException.class, () -> findService.findById(savedId));
        }

        @Test
        @DisplayName("존재하지 않는 게시글을 삭제시 예외가 발생해야 한다.")
        void delete_post_fail() {

            // given
            CreateUserRequest userRequest = createDefaultUser();
            Long userId = userService.join(userRequest);

            CreateFindRequest findRequest = createDefaultFind(userId);
            Long savedId = findService.join(findRequest);

            // when

            // then
            assertThrows(PostNotFoundException.class, () -> findService.deleteFind(100L));
        }
    }
}
