package org.tanghao.hotpot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 分页数据封装
 *
 * @author TangHao
 * @date 2022-09-21 23:28:18
 */
@Getter
@Schema(description = "分页数据")
public class CommonPage {
    @Schema(description = "第几页")
    private Long currentNo;

    @Schema(description = "总页数")
    private Long totalPages;

    @Schema(description = "每页显示条数")
    private Integer pageSize;

    @Schema(description = "总条数")
    private Long count;

    @Schema(description = "查询结果")
    private Object object;

    private CommonPage(Long currentNo, Long totalPages, Integer pageSize, Long count, Object object) {
        this.currentNo = currentNo;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.count = count;
        this.object = object;
    }

    /**
     * 获取分页数据
     *
     * @param currentNo 第几页
     * @param pageSize 每页显示条数
     * @param count 总条数
     * @param object 查询结果
     * @return 分页数据
     */
    public static CommonPage of(Long currentNo, Integer pageSize, Long count, Object object) {
        // 获取总页数
        Long totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        return new CommonPage(currentNo, totalPages, pageSize, count, object);
    }
}
