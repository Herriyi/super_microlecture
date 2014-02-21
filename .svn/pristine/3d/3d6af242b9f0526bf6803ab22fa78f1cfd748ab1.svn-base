package org.sicdlab.microlecture.business.label.service.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.label.model.enums.LabelObjectType;
import org.sicdlab.microlecture.business.label.service.LabelService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Label;
import org.sicdlab.microlecture.common.bean.LabelObject;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;






@Component
@Transactional
public class LabelServiceImpl extends BaseServiceImpl implements LabelService{

	@Override
	public String getTenHotLabels() {
		// TODO Auto-generated method stub
		String labels = "";
		List<Label> labelList = (List<Label>) getCurrentSession().createCriteria(Label.class).addOrder(Order.desc("frequency")).list();
		Collections.shuffle(labelList); 
		for(int i = 0; i<labelList.size(); i++){
			labels += labelList.get(i).getLabelName() + ",";
		}
		return labels;
	}

	@Override
	public String getObjectLabels(String objectId, String objectType) {
		// TODO Auto-generated method stub
		String labels  = "";
		List<LabelObject> labelObjectList = (List<LabelObject>) getCurrentSession().createCriteria(LabelObject.class)
				.add(Restrictions.eq("objectId", objectId)).add(Restrictions.eq("objectType", objectType)).list();
		for(int i = 0; i<labelObjectList.size(); i++){
			labels += labelObjectList.get(i).getLabel().getLabelName() + ",";
		}
		return labels;
	}

	@Override
	@Transactional
	public void saveObjectLabels(String labels, String objectId,
			String objectType) {
		// TODO Auto-generated method stub
		List<LabelObject> labelObjectList = (List<LabelObject>) getCurrentSession().createCriteria(LabelObject.class)
				.add(Restrictions.eq("objectId", objectId)).add(Restrictions.eq("objectType", objectType)).list();
		deleteAll(labelObjectList);
		String labelArray[] = labels.split(",");
		for(int i = 0; i<labelArray.length; i++){
			List<Label> labelList = (List<Label>) getCurrentSession().createCriteria(Label.class).add(Restrictions.eq("labelName", labelArray[i])).list();
			if(labelList.size()==1){
				labelList.get(0).setFrequency(labelList.get(0).getFrequency()+1);
				getCurrentSession().update(labelList.get(0));
				
				LabelObject labelObject = new LabelObject();
				labelObject.setLabelObjectId(UUIDGenerator.randomUUID().toString());
				labelObject.setLabel(labelList.get(0));
				labelObject.setObjectId(objectId);
				labelObject.setObjectType(objectType);
				getCurrentSession().save(labelObject);

			} else {				
				Label label = new Label();
				label.setLabelId(UUIDGenerator.randomUUID().toString());
				label.setLabelName(labelArray[i]);
				label.setFrequency(1);
				getCurrentSession().save(label);
				
				LabelObject labelObject = new LabelObject();
				labelObject.setLabelObjectId(UUIDGenerator.randomUUID().toString());
				labelObject.setLabel(label);
				labelObject.setObjectId(objectId);
				labelObject.setObjectType(objectType);
				getCurrentSession().save(labelObject);
			}
		}
		
	}

	@Override
	public void deleteObjectLabels(String objectId, LabelObjectType objectType) {
		// TODO Auto-generated method stub
		
	}

}
