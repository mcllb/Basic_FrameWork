package com.yunke.admin.modular.system.region.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.region.enums.RegionLevelEnum;
import com.yunke.admin.modular.system.region.mapper.RegionMapper;
import com.yunke.admin.modular.system.region.model.entity.Region;
import com.yunke.admin.modular.system.region.model.param.RegionAddParam;
import com.yunke.admin.modular.system.region.model.param.RegionEditParam;
import com.yunke.admin.modular.system.region.model.param.RegionEnableParam;
import com.yunke.admin.modular.system.region.model.param.RegionPageQueryParam;
import com.yunke.admin.modular.system.region.model.vo.RegionVO;
import com.yunke.admin.modular.system.region.service.RegionService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @className RegionServiceImpl
 * @description: 行政区划代码表_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class RegionServiceImpl extends GeneralServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public PageWrapper<RegionVO> pageVO(RegionPageQueryParam pageQueryParam) {
        LambdaQueryWrapper<Region> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：xxx

        // 查询条件：xxx

        // 排序
        lambdaQueryWrapper.orderByDesc(Region::getCreateTime);

        Page<Region> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<Region> records = pageData.getRecords();
        List<RegionVO> voList = BeanUtil.copyListProperties(records, RegionVO::new);
        BeanUtil.fillListDataDictField(voList);
        BeanUtil.fillListEnumDictField(voList);
        PageWrapper<RegionVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

    @Override
    public List<Region> selectTreeTable(RegionPageQueryParam pageQueryParam) {
        RegionService regionService = SpringUtil.getBean(RegionService.class);
        ThreadPoolTaskExecutor executor = SpringUtil.getBean(ThreadPoolTaskExecutor.class);
        CompletableFuture<List<Region>> futureP = CompletableFuture.supplyAsync(() -> {
            List<Region> ret = CollUtil.newArrayList();
            ret = regionService.lambdaQuery().eq(Region::getLevel, RegionLevelEnum.PROVINCE.getCode()).orderByAsc(Region::getId).list();
            return ret;
        }, executor);

        CompletableFuture<List<Region>> futureC = CompletableFuture.supplyAsync(() -> {
            TimeInterval timer = DateUtil.timer();
            List<Region> ret = CollUtil.newArrayList();
            ret = regionService.lambdaQuery().eq(Region::getLevel, RegionLevelEnum.CITY.getCode()).orderByAsc(Region::getId).list();
            return ret;
        }, executor);

        CompletableFuture<List<Region>> futureA = CompletableFuture.supplyAsync(() -> {
            List<Region> ret = CollUtil.newArrayList();
            ret = regionService.lambdaQuery().eq(Region::getLevel, RegionLevelEnum.AREA.getCode()).orderByAsc(Region::getId).list();
            return ret;
        }, executor);

        CompletableFuture<List<Region>> futureT = CompletableFuture.supplyAsync(() -> {
            List<Region> ret = CollUtil.newArrayList();
            ret = regionService.lambdaQuery().eq(Region::getLevel, RegionLevelEnum.TOWN.getCode()).orderByAsc(Region::getId).list();
            return ret;
        }, executor);

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureP, futureC, futureA, futureT);
        allFuture.join();

        List<Region> listP = futureP.join();
        List<Region> listC = futureC.join();
        List<Region> listA = futureA.join();
        List<Region> listT = futureT.join();
        if(listA != null && listT != null){
            Map<String, List<Region>> collect = listT.stream().collect(Collectors.groupingBy(Region::getParentId));
            listA.forEach(item -> {
                item.setChildren(collect.get(item.getId()));
            });
        }

        if(listC != null && listA != null){
            Map<String, List<Region>> collect = listA.stream().collect(Collectors.groupingBy(Region::getParentId));
            listC.forEach(item -> {
                item.setChildren(collect.get(item.getId()));
            });
        }

        if(listP != null && listC != null){
            Map<String, List<Region>> collect = listC.stream().collect(Collectors.groupingBy(Region::getParentId));
            listP.forEach(item -> {
                item.setChildren(collect.get(item.getId()));
            });
        }
        return listP;
    }

    @Override
    public List<RegionVO> selectListByParentId(String parentId) {
        List<Region> list = this.lambdaQuery().eq(Region::getParentId, parentId)
                .orderByAsc(Region::getId).list();
        List<RegionVO> listVO = BeanUtil.copyListProperties(list, RegionVO::new);
        if(listVO != null && listVO.size() > 0){
            listVO.forEach(item -> {
                int count = this.lambdaQuery().eq(Region::getParentId, item.getId()).count();
                boolean hasChild = count > 0;
                item.setHasChild(hasChild);
            });
        }
        return listVO;
    }

    @Override
    public RegionVO getVO(String id) {
        RegionVO regionVO = new RegionVO();
        Region region = this.getById(id);
        BeanUtil.copyProperties(region, regionVO);
        return regionVO;
    }

    @Transactional
    @Override
    public boolean add(RegionAddParam addParam) {
        Region region = new Region();
        BeanUtil.copyProperties(addParam, region);
        checkParam(region,false);
        return this.save(region);
    }

    @Transactional
    @Override
    public boolean edit(RegionEditParam editParam) {
        Region region = new Region();
        BeanUtil.copyProperties(editParam, region);
        checkParam(region,true);
        return this.updateById(region);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        Integer countChild = this.lambdaQuery().eq(Region::getParentId, id).count();
        if(countChild > 0){
            throw new ServiceException("存在子节点不能删除");
        }
        return this.removeById(id);
    }

    @Override
    public boolean updateEnable(RegionEnableParam enableParam) {
        Region region = new Region();
        region.setId(enableParam.getId());
        region.setEnable(enableParam.getEnable());
        return this.updateById(region);
    }


    private void checkParam(Region entity,boolean isExcludeSelf){
        LambdaQueryWrapper<Region> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 校验行政区划代码是否重复
        lambdaQueryWrapper.eq(Region::getId, entity.getId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Region::getId, entity.getId());
        }
        int countId = this.count(lambdaQueryWrapper);
        if(countId > 0){
            throw new ServiceException("行政区划代码已存在");
        }

        // 校验同级行政区划名称是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(Region::getRegionName, entity.getRegionName());
        lambdaQueryWrapper.eq(Region::getParentId, entity.getParentId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Region::getId, entity.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException("同层级行政区划名称重复");
        }

        // 校验同级小组号是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(Region::getRegionCode, entity.getRegionCode());
        lambdaQueryWrapper.eq(Region::getParentId, entity.getParentId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Region::getId, entity.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException("同层级小组号重复重复");
        }

    }
}
