package arom_semo.server.domain.group.controller;

import arom_semo.server.domain.group.dto.GroupResponseDto;
import arom_semo.server.domain.group.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private GroupController groupController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    void fetchMyGroupsTest() throws Exception{
        // given
        GroupResponseDto group1 = new GroupResponseDto("group1", 10, 1, 100, 11, null);
        GroupResponseDto group2 = new GroupResponseDto("group2", 50, 5, 19, 17, null);
        List<GroupResponseDto> groups = Arrays.asList(group1, group2);

        when(groupService.fetchMyGroups(anyLong())).thenReturn(groups);

        // when & then
        mockMvc.perform(get("/api/v1/groups/my-groups")
                .param("memberId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("group1"))
                .andExpect(jsonPath("$[0].maxParticipants").value(10))
                .andExpect(jsonPath("$[1].groupName").value("group2"))
                .andExpect(jsonPath("$[1].maxParticipants").value(50));

        verify(groupService, times(1)).fetchMyGroups(anyLong());
    }
}