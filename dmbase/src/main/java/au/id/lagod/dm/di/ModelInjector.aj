package au.id.lagod.dm.di;

import au.id.lagod.dm.collections.Model;

public aspect ModelInjector {
private Model model;

public pointcut beanConstruction(Object bean) : 
	initialization(ModelClient+.new(..)) && this(bean);

after(Object bean) returning : 
	beanConstruction(bean) {
	configureBean(bean);
}

public void configureBean(Object bean) {
    ((ModelClient)bean).setModel(this.model);
}
       
public void setModel(Model model) {
	if (this.model != null) {
		// Somebody has alread configured this aspect.  
		throw new Error("Another model has already been configured into the ModelInjector");
	}
    this.model = model;
}

public void resetModel() {
	this.model = null;
}

public Model getModel() {
	return model;
}

public boolean hasModel() {
	return this.model != null;
}

}
