var vm = new Vue({
	el:'#app',
	data:{
		message:'Hello world',
		oncemessage:'once',
		one:"1",
		two:"2",
		opr:'',
		active:true,
		list:[
		     
		]
	},
	computed:{
		result:{ 
			get:function(){
				switch(this.opr){
				case '+':
					return this.result = parseInt(this.one)+parseInt(this.two);
				case '-':
					return this.result = parseInt(this.one)-parseInt(this.two);
				case 'x':
					return this.result = parseInt(this.one)*parseInt(this.two);
				case '/':
					return this.result = parseInt(this.one)/parseInt(this.two);
				default:
					return this.result = parseInt(this.one)+parseInt(this.two);	
				}				
			},
			set:function(v){
				temp={
					opera:this.one+this.opr+this.two,
					result:v
				}
				this.list.push(temp);
			}
		}
	
	},
	methods:{
		showEvent:function(event){
			alert(event);
			console.log(this.$els.demo.innerText);
		}
	},
	directives : {
		'global-directive':{
		    bind: function(){
		        console.log('global-directive-bind',arguments);
		    },
		    update: function(newValue, oldValue){
		        console.log('global-directive-update',newValue,oldValue);
		    },
		    unbind: function(){
		        console.log('global-directive-unbind', arguments);
		    }
		}
	},
	init:function(){
		//console.log('init');
	},//在实例开始初始化时同步调用
	created:function(){
		//console.log('created');
	},//在实例创建之后调用
	beforeMount:function(){
		//console.log('beforeMount');
	},//在mounted之前运行
	mounted:function(){
		//console.log('mounted');
		//this.$destroy();
	},//在编译结束后调用，此时所有指令已生效，数据变化已能触发DOM更新
	beforeDestroy:function(){
		//console.log('beforeDestroy');
	},//在开始销毁实例时调用，此刻实例仍然有效
	destroyed:function(){
		//console.log('destroyed');
	},//在实例被销毁之后调用。此时所有绑定和实例指令都已经解绑，子实例也被销毁
	beforeUpdate:function(){
		//console.log('beforeUpdate');
	},//在实例挂载之后，再次更新实例（例如更新data）时会调用该方法，此时尚未更新DOM 结构
	updated:function(){
		//console.log('updated');
	},//在实例挂载之后，再次更新实例并更新完DOM 结构后调用
	activated:function(){
		//console.log('activated');
	},//需要配合动态组件keep-live 属性使用。在动态组件初始化渲染的过程中调用该方法
	deactivated:function(){
		//console.log('deactived');
	}//需要配合动态组件keep-live 属性使用。在动态组件移出的过程中调用该方法
		
});

var comp = Vue.extend({
	directives : {
		'localDirective' : {} //注册局部自定义指令，可在当前组件内通过v-local-directive方式调用
	}
});

Vue.directive('global-directive',{
    bind: function(){
        console.log('global-directive-bind',arguments);
    },
    update: function(newValue, oldValue){
        console.log('global-directive-update',newValue,oldValue);
    },
    unbind: function(){
        console.log('global-directive-unbind', arguments);
    }
});
