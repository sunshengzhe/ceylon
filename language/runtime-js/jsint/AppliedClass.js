function AppliedClass$jsint(tipo,$a$,that,classTargs){
  if (!$a$.Type$AppliedClass)$a$.Type$AppliedClass=$a$.Type$Class;
  if (!$a$.Arguments$AppliedClass)$a$.Arguments$AppliedClass=$a$.Arguments$Class;
  $i$AppliedClass$jsint();
  var _proto=AppliedClass$jsint.$$.prototype;
  if (that===undefined){
    var mm = getrtmm$$(tipo);
    if (mm && mm.$cont) {
      that=function AppliedClass1(x){/*Class*/
        if (that.$targs) {
          var _a=[];
          for (var i=0;i<arguments.length;i++)_a.push(arguments[i]);
          _a.push(that.$targs);
          return tipo.apply(x,_a);
        }
        return tipo.apply(x,arguments);
      }
    } else {
      that=function AppliedClass2(){
        if (that.$targs) {
          var _a=[];
          for (var i=0;i<arguments.length;i++)_a.push(arguments[i]);
          _a.push(that.$targs);
          return tipo.apply(undefined,_a);
        }
        return tipo.apply(undefined,arguments);
      }
    }
    that.$m$=mm;
    var dummy = new AppliedClass$jsint.$$;
    that.$$=AppliedClass$jsint.$$;
    that.getT$all=function(){return dummy.getT$all();};
    that.getT$name=function(){return dummy.getT$name();};
    that.$_apply=_proto.$_apply;
    that.namedApply=_proto.namedApply;
  }
  //AppliedMemberClass also gets these
  atr$(that,'satisfiedTypes',function(){return coisattype$(that);
  },undefined,_proto.$prop$getExtendedType.$m$);
  atr$(that,'container',function(){ return coicont$(that);
  },undefined,_proto.$prop$getContainer.$m$);
  atr$(that,'string',function(){return coistr$(that);},undefined,_proto.$prop$getString.$m$);
  atr$(that,'hash',function(){return coihash$(that);},undefined,_proto.$prop$getHash.$m$);
  atr$(that,'typeArguments',function(){return coitarg$(that);
  },undefined,_proto.$prop$getTypeArguments.$m$);
  atr$(that,'typeArgumentList',function(){return coitargl$(that);
  },undefined,_proto.$prop$getTypeArgumentList.$m$);
  atr$(that,'typeArgumentWithVariances',function(){return coitargv$(that);
  },undefined,_proto.$prop$getTypeArgumentWithVariances.$m$);
  atr$(that,'typeArgumentWithVarianceList',function(){return coitargvl$(that);
  },undefined,_proto.$prop$getTypeArgumentWithVarianceList.$m$);
  atr$(that,'extendedType',function(){return coiexttype$(that);
  },undefined,_proto.$prop$getExtendedType.$m$);
  atr$(that,'declaration',function(){return coimoddcl$(that);
  },undefined,_proto.$prop$getDeclaration.$m$);
  atr$(that,'caseValues',function(){return coicase$(that);
  },undefined,_proto.$prop$getCaseValues.$m$);
  atr$(that,'defaultConstructor',function(){
    return _proto.$prop$getDefaultConstructor.get.call(that);
  },undefined,_proto.$prop$getDefaultConstructor.$m$);
  that.getMethod=_proto.getMethod;
  that.getDeclaredMethod=_proto.getDeclaredMethod;
  that.getMethods=_proto.getMethods;
  that.getDeclaredMethods=_proto.getDeclaredMethods;
  that.getAttribute=_proto.getAttribute;
  that.getDeclaredAttribute=_proto.getDeclaredAttribute;
  that.getAttributes=_proto.getAttributes;
  that.getDeclaredAttributes=_proto.getDeclaredAttributes;
  that.getClassOrInterface=_proto.getClassOrInterface;
  that.getDeclaredClassOrInterface=_proto.getDeclaredClassOrInterface;
  that.getClass=_proto.getClass;
  that.getDeclaredClass=_proto.getDeclaredClass;
  that.getClasses=_proto.getClasses;
  that.getDeclaredClasses=_proto.getDeclaredClasses;
  that.getInterface=_proto.getInterface;
  that.getDeclaredInterface=_proto.getDeclaredInterface;
  that.getInterfaces=_proto.getInterfaces;
  that.getDeclaredInterfaces=_proto.getDeclaredInterfaces;
  that.getConstructor=_proto.getConstructor;
  that.getCallableConstructors=_proto.getCallableConstructors;
  that.getDeclaredCallableConstructors=_proto.getDeclaredCallableConstructors;
  that.getDeclaredConstructor=_proto.getDeclaredConstructor;
  that.getDeclaredValueConstructors=_proto.getDeclaredValueConstructors;
  that.getValueConstructors=_proto.getValueConstructors;
  that.equals=_proto.equals;
  that.typeOf=_proto.typeOf;
  that.supertypeOf=_proto.supertypeOf;
  that.subtypeOf=_proto.subtypeOf;
  that.exactly=_proto.exactly;
  that.union=_proto.union;
  that.intersection=_proto.intersection;
  that.$targs=classTargs;
  set_type_args(that,$a$,AppliedClass$jsint);
  Class$meta$model({Arguments$Class:that.$a$.Arguments$AppliedClass,
                   Type$Class:that.$a$.Type$AppliedClass},that);
  //This is for serialization
  if (tipo===Tuple && classTargs)that.$a$.Type$Class={t:Tuple,a:classTargs};
  that.tipo=tipo;
  return that;
}
