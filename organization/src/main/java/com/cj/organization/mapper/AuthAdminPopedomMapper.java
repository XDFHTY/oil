package com.cj.organization.mapper;

import com.cj.common.entity.AuthAdminPopedom;
import com.cj.common.entity.FactoryType;
import com.cj.organization.entity.OrganInfo;
import com.cj.organization.entity.RespAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthAdminPopedomMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long authAdminPopedom);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthAdminPopedom record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthAdminPopedom record);

    /**
     *
     * @mbggenerated
     */
    AuthAdminPopedom selectByPrimaryKey(Long authAdminPopedom);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthAdminPopedom record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthAdminPopedom record);

    //添加auth_admin_popedom表记录
    int addRecord(@Param("adminId") Long adminId, @Param("popedomId")Long factoryId, @Param("i")int i);

    //根据区域id 和辖区分类id 查询这个区域是否已经添加了管理员
    AuthAdminPopedom findDataByPopedomId(@Param("popId") Long popId,@Param("typeId") int typeId);

    //查询管理人账号和姓名
    Map findAdminById(@Param("popId") Long station_core_id, @Param("typeId") int i);

    //根据联系人姓名和角色等级 模糊查询 账号列表
    List<RespAdmin> findAdminListByStation(@Param("name") String name, @Param("roleName") String roleName);

    //根据管理员id查询是否分配了机构
    AuthAdminPopedom findDataByAdminId(Map map);

    //删除记录
    void deleteRecord(@Param("adminId")Integer adminId, @Param("popId")Integer stationId,  @Param("popGrade")int i);

    //根据场站id 和 adminId 查询是否已经绑定了关系
    AuthAdminPopedom findAdminByStationId(@Param("popId")Long popId,@Param("adminId")Long adminId, @Param("popGrade")Integer popGrade);

    //根据场站id 修改管理员id
    void updateAdminIdByStationId( @Param("adminId")Long adminId, @Param("popId")Long popId,  @Param("popGrade")int i);

    //添加账号姓名 手机号
    int insertAdminInfo(Map map);

    //修改角色
    void updateAdminAndRole( @Param("adminId")Integer adminId,  @Param("roleId")Integer roleId);

    //根据adminId 查询账号等级
    String findRoleByAdminId(Integer adminId);

    List<RespAdmin> findAdminListByArea(@Param("name") String name, @Param("roleName") String roleName);

    List<RespAdmin> findAdminListByFactory(@Param("name") String name, @Param("roleName") String roleName);

    //修改adminInfo的信息
    void updateAdminInfoById(Map map);

    //移除管理员关联机构记录
    int deleteRecordByAdminIdAndPopId(@Param("adminId")Integer adminId, @Param("stationId")Integer stationId);

    //根据账号等级 查询未绑定机构的账号  模糊条件 姓名
    List<RespAdmin> findAdminByRoleId(@Param("name") String name, @Param("list") List<Integer> list2);

    //查询 机构有几个管理人
    int findPopCountById(@Param("popId")Integer popId, @Param("popType")int i);

    //根据场站id 查询场站名称
    String findStationNameById(Integer stationId);

    //根据作业区id  查询作业区名称
    String findAreaNameById(Integer areaId);

    //根据气矿id 查询气矿名称
    String findFactoryNameById(Integer factoryId);

    //根据管理员id 查询机构Id
    Long findPopIdByAdminId(@Param("adminId") Long adminId, @Param("type") int i);

    //查询所有工艺分类
    List<FactoryType> findFactoryTypeList();




    //查询账号对应组织机构信息
    List<List<?>> findAdminByOrgan(Map map);

    //查询组织机构对应账号信息
    List<OrganInfo> findOrganInfo(Map map2);

    //查询管理员姓名
    String findFullNameById(Long adminId);

    //查询此作业区下 所有场站级账号
    List<RespAdmin> findAdminByTaskAreaId(Integer areaId);

    //根据adminId查询角色id
    Integer findRoleIdByAdminId(Integer adminId1);

    //根据场站id查询工艺分类名称
    String findFactoryNameByStationId(Long stationId);

    //查询adminInfo表中是否存在信息
    int findAdminInfoById(Map map);
}