package pl.skowrxn.cookie.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.admin.dto.VisitorListEntryDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorListResponse {

    private List<VisitorListEntryDTO> visitors;
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;

}
