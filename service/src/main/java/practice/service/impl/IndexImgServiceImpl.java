package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.entity.IndexImg;
import practice.mapper.IndexImgMapper;
import practice.repository.IndexImgRepository;
import practice.service.IndexImgService;
import practice.vo.ImgVo;

import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    IndexImgMapper indexImgMapper;

    @Autowired
    IndexImgRepository indexImgRepository;
    @Override
    public List<ImgVo> listIndexImgs() {
        List<IndexImg> indexImgs = indexImgRepository.findActiveOnesAndSortedBySeq();
        return indexImgMapper.toVo(indexImgs);
    }
}
