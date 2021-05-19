//------------------------------------------------------------------------------
// <auto-generated>
//     Dieser Code wurde von einem Tool generiert.
//     Laufzeitversion:4.0.30319.42000
//
//     Änderungen an dieser Datei können falsches Verhalten verursachen und gehen verloren, wenn
//     der Code erneut generiert wird.
// </auto-generated>
//------------------------------------------------------------------------------

using NMF.Collections.Generic;
using NMF.Collections.ObjectModel;
using NMF.Expressions;
using NMF.Expressions.Linq;
using NMF.Models;
using NMF.Models.Collections;
using NMF.Models.Expressions;
using NMF.Models.Meta;
using NMF.Models.Repository;
using NMF.Serialization;
using NMF.Utilities;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Diagnostics;
using System.Globalization;
using System.Linq;

namespace TTC2021.OclToSql.Ocl.Dm
{
    
    
    /// <summary>
    /// The default implementation of the Entity class
    /// </summary>
    [XmlIdentifierAttribute("name")]
    [XmlNamespaceAttribute("http://www.example.org/ocl/dm")]
    [XmlNamespacePrefixAttribute("DM")]
    [ModelRepresentationClassAttribute("http://www.example.org/ocl#//dm/Entity")]
    [DebuggerDisplayAttribute("Entity {Name}")]
    public partial class Entity : ModelElement, IEntity, IModelElement
    {
        
        /// <summary>
        /// The backing field for the Name property
        /// </summary>
        [DebuggerBrowsableAttribute(DebuggerBrowsableState.Never)]
        private string _name;
        
        private static Lazy<ITypedElement> _nameAttribute = new Lazy<ITypedElement>(RetrieveNameAttribute);
        
        private static Lazy<ITypedElement> _endsReference = new Lazy<ITypedElement>(RetrieveEndsReference);
        
        /// <summary>
        /// The backing field for the Ends property
        /// </summary>
        [DebuggerBrowsableAttribute(DebuggerBrowsableState.Never)]
        private ObservableCompositionOrderedSet<IAssociationEnd> _ends;
        
        private static Lazy<ITypedElement> _attributesReference = new Lazy<ITypedElement>(RetrieveAttributesReference);
        
        /// <summary>
        /// The backing field for the Attributes property
        /// </summary>
        [DebuggerBrowsableAttribute(DebuggerBrowsableState.Never)]
        private ObservableCompositionOrderedSet<TTC2021.OclToSql.Ocl.Dm.IAttribute> _attributes;
        
        private static IClass _classInstance;
        
        public Entity()
        {
            this._ends = new ObservableCompositionOrderedSet<IAssociationEnd>(this);
            this._ends.CollectionChanging += this.EndsCollectionChanging;
            this._ends.CollectionChanged += this.EndsCollectionChanged;
            this._attributes = new ObservableCompositionOrderedSet<TTC2021.OclToSql.Ocl.Dm.IAttribute>(this);
            this._attributes.CollectionChanging += this.AttributesCollectionChanging;
            this._attributes.CollectionChanged += this.AttributesCollectionChanged;
        }
        
        /// <summary>
        /// The name property
        /// </summary>
        [DisplayNameAttribute("name")]
        [CategoryAttribute("Entity")]
        [XmlElementNameAttribute("name")]
        [IdAttribute()]
        [XmlAttributeAttribute(true)]
        public string Name
        {
            get
            {
                return this._name;
            }
            set
            {
                if ((this._name != value))
                {
                    string old = this._name;
                    ValueChangedEventArgs e = new ValueChangedEventArgs(old, value);
                    this.OnNameChanging(e);
                    this.OnPropertyChanging("Name", e, _nameAttribute);
                    this._name = value;
                    this.OnNameChanged(e);
                    this.OnPropertyChanged("Name", e, _nameAttribute);
                }
            }
        }
        
        /// <summary>
        /// The ends property
        /// </summary>
        [DesignerSerializationVisibilityAttribute(DesignerSerializationVisibility.Content)]
        [BrowsableAttribute(false)]
        [XmlElementNameAttribute("ends")]
        [XmlAttributeAttribute(false)]
        [ContainmentAttribute()]
        [ConstantAttribute()]
        public IOrderedSetExpression<IAssociationEnd> Ends
        {
            get
            {
                return this._ends;
            }
        }
        
        /// <summary>
        /// The attributes property
        /// </summary>
        [DesignerSerializationVisibilityAttribute(DesignerSerializationVisibility.Content)]
        [BrowsableAttribute(false)]
        [XmlElementNameAttribute("attributes")]
        [XmlAttributeAttribute(false)]
        [ContainmentAttribute()]
        [ConstantAttribute()]
        public IOrderedSetExpression<TTC2021.OclToSql.Ocl.Dm.IAttribute> Attributes
        {
            get
            {
                return this._attributes;
            }
        }
        
        /// <summary>
        /// Gets the child model elements of this model element
        /// </summary>
        public override IEnumerableExpression<IModelElement> Children
        {
            get
            {
                return base.Children.Concat(new EntityChildrenCollection(this));
            }
        }
        
        /// <summary>
        /// Gets the referenced model elements of this model element
        /// </summary>
        public override IEnumerableExpression<IModelElement> ReferencedElements
        {
            get
            {
                return base.ReferencedElements.Concat(new EntityReferencedElementsCollection(this));
            }
        }
        
        /// <summary>
        /// Gets the Class model for this type
        /// </summary>
        public new static IClass ClassInstance
        {
            get
            {
                if ((_classInstance == null))
                {
                    _classInstance = ((IClass)(MetaRepository.Instance.Resolve("http://www.example.org/ocl#//dm/Entity")));
                }
                return _classInstance;
            }
        }
        
        /// <summary>
        /// Gets a value indicating whether the current model element can be identified by an attribute value
        /// </summary>
        public override bool IsIdentified
        {
            get
            {
                return true;
            }
        }
        
        /// <summary>
        /// Gets fired before the Name property changes its value
        /// </summary>
        public event System.EventHandler<ValueChangedEventArgs> NameChanging;
        
        /// <summary>
        /// Gets fired when the Name property changed its value
        /// </summary>
        public event System.EventHandler<ValueChangedEventArgs> NameChanged;
        
        private static ITypedElement RetrieveNameAttribute()
        {
            return ((ITypedElement)(((ModelElement)(TTC2021.OclToSql.Ocl.Dm.Entity.ClassInstance)).Resolve("name")));
        }
        
        /// <summary>
        /// Raises the NameChanging event
        /// </summary>
        /// <param name="eventArgs">The event data</param>
        protected virtual void OnNameChanging(ValueChangedEventArgs eventArgs)
        {
            System.EventHandler<ValueChangedEventArgs> handler = this.NameChanging;
            if ((handler != null))
            {
                handler.Invoke(this, eventArgs);
            }
        }
        
        /// <summary>
        /// Raises the NameChanged event
        /// </summary>
        /// <param name="eventArgs">The event data</param>
        protected virtual void OnNameChanged(ValueChangedEventArgs eventArgs)
        {
            System.EventHandler<ValueChangedEventArgs> handler = this.NameChanged;
            if ((handler != null))
            {
                handler.Invoke(this, eventArgs);
            }
        }
        
        private static ITypedElement RetrieveEndsReference()
        {
            return ((ITypedElement)(((ModelElement)(TTC2021.OclToSql.Ocl.Dm.Entity.ClassInstance)).Resolve("ends")));
        }
        
        /// <summary>
        /// Forwards CollectionChanging notifications for the Ends property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change</param>
        /// <param name="e">The original event data</param>
        private void EndsCollectionChanging(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanging("Ends", e, _endsReference);
        }
        
        /// <summary>
        /// Forwards CollectionChanged notifications for the Ends property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change</param>
        /// <param name="e">The original event data</param>
        private void EndsCollectionChanged(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanged("Ends", e, _endsReference);
        }
        
        private static ITypedElement RetrieveAttributesReference()
        {
            return ((ITypedElement)(((ModelElement)(TTC2021.OclToSql.Ocl.Dm.Entity.ClassInstance)).Resolve("attributes")));
        }
        
        /// <summary>
        /// Forwards CollectionChanging notifications for the Attributes property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change</param>
        /// <param name="e">The original event data</param>
        private void AttributesCollectionChanging(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanging("Attributes", e, _attributesReference);
        }
        
        /// <summary>
        /// Forwards CollectionChanged notifications for the Attributes property to the parent model element
        /// </summary>
        /// <param name="sender">The collection that raised the change</param>
        /// <param name="e">The original event data</param>
        private void AttributesCollectionChanged(object sender, NotifyCollectionChangedEventArgs e)
        {
            this.OnCollectionChanged("Attributes", e, _attributesReference);
        }
        
        /// <summary>
        /// Gets the relative URI fragment for the given child model element
        /// </summary>
        /// <returns>A fragment of the relative URI</returns>
        /// <param name="element">The element that should be looked for</param>
        protected override string GetRelativePathForNonIdentifiedChild(IModelElement element)
        {
            int endsIndex = ModelHelper.IndexOfReference(this.Ends, element);
            if ((endsIndex != -1))
            {
                return ModelHelper.CreatePath("ends", endsIndex);
            }
            int attributesIndex = ModelHelper.IndexOfReference(this.Attributes, element);
            if ((attributesIndex != -1))
            {
                return ModelHelper.CreatePath("attributes", attributesIndex);
            }
            return base.GetRelativePathForNonIdentifiedChild(element);
        }
        
        /// <summary>
        /// Resolves the given URI to a child model element
        /// </summary>
        /// <returns>The model element or null if it could not be found</returns>
        /// <param name="reference">The requested reference name</param>
        /// <param name="index">The index of this reference</param>
        protected override IModelElement GetModelElementForReference(string reference, int index)
        {
            if ((reference == "ENDS"))
            {
                if ((index < this.Ends.Count))
                {
                    return this.Ends[index];
                }
                else
                {
                    return null;
                }
            }
            if ((reference == "ATTRIBUTES"))
            {
                if ((index < this.Attributes.Count))
                {
                    return this.Attributes[index];
                }
                else
                {
                    return null;
                }
            }
            return base.GetModelElementForReference(reference, index);
        }
        
        /// <summary>
        /// Resolves the given attribute name
        /// </summary>
        /// <returns>The attribute value or null if it could not be found</returns>
        /// <param name="attribute">The requested attribute name</param>
        /// <param name="index">The index of this attribute</param>
        protected override object GetAttributeValue(string attribute, int index)
        {
            if ((attribute == "NAME"))
            {
                return this.Name;
            }
            return base.GetAttributeValue(attribute, index);
        }
        
        /// <summary>
        /// Gets the Model element collection for the given feature
        /// </summary>
        /// <returns>A non-generic list of elements</returns>
        /// <param name="feature">The requested feature</param>
        protected override System.Collections.IList GetCollectionForFeature(string feature)
        {
            if ((feature == "ENDS"))
            {
                return this._ends;
            }
            if ((feature == "ATTRIBUTES"))
            {
                return this._attributes;
            }
            return base.GetCollectionForFeature(feature);
        }
        
        /// <summary>
        /// Sets a value to the given feature
        /// </summary>
        /// <param name="feature">The requested feature</param>
        /// <param name="value">The value that should be set to that feature</param>
        protected override void SetFeature(string feature, object value)
        {
            if ((feature == "NAME"))
            {
                this.Name = ((string)(value));
                return;
            }
            base.SetFeature(feature, value);
        }
        
        /// <summary>
        /// Gets the property expression for the given attribute
        /// </summary>
        /// <returns>An incremental property expression</returns>
        /// <param name="attribute">The requested attribute in upper case</param>
        protected override NMF.Expressions.INotifyExpression<object> GetExpressionForAttribute(string attribute)
        {
            if ((attribute == "NAME"))
            {
                return new NameProxy(this);
            }
            return base.GetExpressionForAttribute(attribute);
        }
        
        /// <summary>
        /// Gets the property name for the given container
        /// </summary>
        /// <returns>The name of the respective container reference</returns>
        /// <param name="container">The container object</param>
        protected override string GetCompositionName(object container)
        {
            if ((container == this._ends))
            {
                return "ends";
            }
            if ((container == this._attributes))
            {
                return "attributes";
            }
            return base.GetCompositionName(container);
        }
        
        /// <summary>
        /// Gets the Class for this model element
        /// </summary>
        public override IClass GetClass()
        {
            if ((_classInstance == null))
            {
                _classInstance = ((IClass)(MetaRepository.Instance.Resolve("http://www.example.org/ocl#//dm/Entity")));
            }
            return _classInstance;
        }
        
        /// <summary>
        /// Gets the identifier string for this model element
        /// </summary>
        /// <returns>The identifier string</returns>
        public override string ToIdentifierString()
        {
            if ((this.Name == null))
            {
                return null;
            }
            return this.Name.ToString();
        }
        
        /// <summary>
        /// The collection class to to represent the children of the Entity class
        /// </summary>
        public class EntityChildrenCollection : ReferenceCollection, ICollectionExpression<IModelElement>, ICollection<IModelElement>
        {
            
            private Entity _parent;
            
            /// <summary>
            /// Creates a new instance
            /// </summary>
            public EntityChildrenCollection(Entity parent)
            {
                this._parent = parent;
            }
            
            /// <summary>
            /// Gets the amount of elements contained in this collection
            /// </summary>
            public override int Count
            {
                get
                {
                    int count = 0;
                    count = (count + this._parent.Ends.Count);
                    count = (count + this._parent.Attributes.Count);
                    return count;
                }
            }
            
            protected override void AttachCore()
            {
                this._parent.Ends.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
                this._parent.Attributes.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
            }
            
            protected override void DetachCore()
            {
                this._parent.Ends.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
                this._parent.Attributes.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
            }
            
            /// <summary>
            /// Adds the given element to the collection
            /// </summary>
            /// <param name="item">The item to add</param>
            public override void Add(IModelElement item)
            {
                IAssociationEnd endsCasted = item.As<IAssociationEnd>();
                if ((endsCasted != null))
                {
                    this._parent.Ends.Add(endsCasted);
                }
                TTC2021.OclToSql.Ocl.Dm.IAttribute attributesCasted = item.As<TTC2021.OclToSql.Ocl.Dm.IAttribute>();
                if ((attributesCasted != null))
                {
                    this._parent.Attributes.Add(attributesCasted);
                }
            }
            
            /// <summary>
            /// Clears the collection and resets all references that implement it.
            /// </summary>
            public override void Clear()
            {
                this._parent.Ends.Clear();
                this._parent.Attributes.Clear();
            }
            
            /// <summary>
            /// Gets a value indicating whether the given element is contained in the collection
            /// </summary>
            /// <returns>True, if it is contained, otherwise False</returns>
            /// <param name="item">The item that should be looked out for</param>
            public override bool Contains(IModelElement item)
            {
                if (this._parent.Ends.Contains(item))
                {
                    return true;
                }
                if (this._parent.Attributes.Contains(item))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Copies the contents of the collection to the given array starting from the given array index
            /// </summary>
            /// <param name="array">The array in which the elements should be copied</param>
            /// <param name="arrayIndex">The starting index</param>
            public override void CopyTo(IModelElement[] array, int arrayIndex)
            {
                IEnumerator<IModelElement> endsEnumerator = this._parent.Ends.GetEnumerator();
                try
                {
                    for (
                    ; endsEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = endsEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    endsEnumerator.Dispose();
                }
                IEnumerator<IModelElement> attributesEnumerator = this._parent.Attributes.GetEnumerator();
                try
                {
                    for (
                    ; attributesEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = attributesEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    attributesEnumerator.Dispose();
                }
            }
            
            /// <summary>
            /// Removes the given item from the collection
            /// </summary>
            /// <returns>True, if the item was removed, otherwise False</returns>
            /// <param name="item">The item that should be removed</param>
            public override bool Remove(IModelElement item)
            {
                IAssociationEnd associationEndItem = item.As<IAssociationEnd>();
                if (((associationEndItem != null) 
                            && this._parent.Ends.Remove(associationEndItem)))
                {
                    return true;
                }
                TTC2021.OclToSql.Ocl.Dm.IAttribute attributeItem = item.As<TTC2021.OclToSql.Ocl.Dm.IAttribute>();
                if (((attributeItem != null) 
                            && this._parent.Attributes.Remove(attributeItem)))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Gets an enumerator that enumerates the collection
            /// </summary>
            /// <returns>A generic enumerator</returns>
            public override IEnumerator<IModelElement> GetEnumerator()
            {
                return Enumerable.Empty<IModelElement>().Concat(this._parent.Ends).Concat(this._parent.Attributes).GetEnumerator();
            }
        }
        
        /// <summary>
        /// The collection class to to represent the children of the Entity class
        /// </summary>
        public class EntityReferencedElementsCollection : ReferenceCollection, ICollectionExpression<IModelElement>, ICollection<IModelElement>
        {
            
            private Entity _parent;
            
            /// <summary>
            /// Creates a new instance
            /// </summary>
            public EntityReferencedElementsCollection(Entity parent)
            {
                this._parent = parent;
            }
            
            /// <summary>
            /// Gets the amount of elements contained in this collection
            /// </summary>
            public override int Count
            {
                get
                {
                    int count = 0;
                    count = (count + this._parent.Ends.Count);
                    count = (count + this._parent.Attributes.Count);
                    return count;
                }
            }
            
            protected override void AttachCore()
            {
                this._parent.Ends.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
                this._parent.Attributes.AsNotifiable().CollectionChanged += this.PropagateCollectionChanges;
            }
            
            protected override void DetachCore()
            {
                this._parent.Ends.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
                this._parent.Attributes.AsNotifiable().CollectionChanged -= this.PropagateCollectionChanges;
            }
            
            /// <summary>
            /// Adds the given element to the collection
            /// </summary>
            /// <param name="item">The item to add</param>
            public override void Add(IModelElement item)
            {
                IAssociationEnd endsCasted = item.As<IAssociationEnd>();
                if ((endsCasted != null))
                {
                    this._parent.Ends.Add(endsCasted);
                }
                TTC2021.OclToSql.Ocl.Dm.IAttribute attributesCasted = item.As<TTC2021.OclToSql.Ocl.Dm.IAttribute>();
                if ((attributesCasted != null))
                {
                    this._parent.Attributes.Add(attributesCasted);
                }
            }
            
            /// <summary>
            /// Clears the collection and resets all references that implement it.
            /// </summary>
            public override void Clear()
            {
                this._parent.Ends.Clear();
                this._parent.Attributes.Clear();
            }
            
            /// <summary>
            /// Gets a value indicating whether the given element is contained in the collection
            /// </summary>
            /// <returns>True, if it is contained, otherwise False</returns>
            /// <param name="item">The item that should be looked out for</param>
            public override bool Contains(IModelElement item)
            {
                if (this._parent.Ends.Contains(item))
                {
                    return true;
                }
                if (this._parent.Attributes.Contains(item))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Copies the contents of the collection to the given array starting from the given array index
            /// </summary>
            /// <param name="array">The array in which the elements should be copied</param>
            /// <param name="arrayIndex">The starting index</param>
            public override void CopyTo(IModelElement[] array, int arrayIndex)
            {
                IEnumerator<IModelElement> endsEnumerator = this._parent.Ends.GetEnumerator();
                try
                {
                    for (
                    ; endsEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = endsEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    endsEnumerator.Dispose();
                }
                IEnumerator<IModelElement> attributesEnumerator = this._parent.Attributes.GetEnumerator();
                try
                {
                    for (
                    ; attributesEnumerator.MoveNext(); 
                    )
                    {
                        array[arrayIndex] = attributesEnumerator.Current;
                        arrayIndex = (arrayIndex + 1);
                    }
                }
                finally
                {
                    attributesEnumerator.Dispose();
                }
            }
            
            /// <summary>
            /// Removes the given item from the collection
            /// </summary>
            /// <returns>True, if the item was removed, otherwise False</returns>
            /// <param name="item">The item that should be removed</param>
            public override bool Remove(IModelElement item)
            {
                IAssociationEnd associationEndItem = item.As<IAssociationEnd>();
                if (((associationEndItem != null) 
                            && this._parent.Ends.Remove(associationEndItem)))
                {
                    return true;
                }
                TTC2021.OclToSql.Ocl.Dm.IAttribute attributeItem = item.As<TTC2021.OclToSql.Ocl.Dm.IAttribute>();
                if (((attributeItem != null) 
                            && this._parent.Attributes.Remove(attributeItem)))
                {
                    return true;
                }
                return false;
            }
            
            /// <summary>
            /// Gets an enumerator that enumerates the collection
            /// </summary>
            /// <returns>A generic enumerator</returns>
            public override IEnumerator<IModelElement> GetEnumerator()
            {
                return Enumerable.Empty<IModelElement>().Concat(this._parent.Ends).Concat(this._parent.Attributes).GetEnumerator();
            }
        }
        
        /// <summary>
        /// Represents a proxy to represent an incremental access to the name property
        /// </summary>
        private sealed class NameProxy : ModelPropertyChange<IEntity, string>
        {
            
            /// <summary>
            /// Creates a new observable property access proxy
            /// </summary>
            /// <param name="modelElement">The model instance element for which to create the property access proxy</param>
            public NameProxy(IEntity modelElement) : 
                    base(modelElement, "name")
            {
            }
            
            /// <summary>
            /// Gets or sets the value of this expression
            /// </summary>
            public override string Value
            {
                get
                {
                    return this.ModelElement.Name;
                }
                set
                {
                    this.ModelElement.Name = value;
                }
            }
        }
    }
}
