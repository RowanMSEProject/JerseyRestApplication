/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mse
 */
@Entity
@Table(name = "EMERGENCYCONTACTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emergencycontacts.findAll", query = "SELECT e FROM Emergencycontacts e"),
    @NamedQuery(name = "Emergencycontacts.findByPersonnelid", query = "SELECT e FROM Emergencycontacts e WHERE e.personnelid = :personnelid"),
    @NamedQuery(name = "Emergencycontacts.findByContact", query = "SELECT e FROM Emergencycontacts e WHERE e.contact = :contact"),
    @NamedQuery(name = "Emergencycontacts.findByRelationship", query = "SELECT e FROM Emergencycontacts e WHERE e.relationship = :relationship"),
    @NamedQuery(name = "Emergencycontacts.findByStreet", query = "SELECT e FROM Emergencycontacts e WHERE e.street = :street"),
    @NamedQuery(name = "Emergencycontacts.findByCity", query = "SELECT e FROM Emergencycontacts e WHERE e.city = :city"),
    @NamedQuery(name = "Emergencycontacts.findByStatecode", query = "SELECT e FROM Emergencycontacts e WHERE e.statecode = :statecode"),
    @NamedQuery(name = "Emergencycontacts.findByPostalcode", query = "SELECT e FROM Emergencycontacts e WHERE e.postalcode = :postalcode"),
    @NamedQuery(name = "Emergencycontacts.findByPhone", query = "SELECT e FROM Emergencycontacts e WHERE e.phone = :phone")})
public class Emergencycontacts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PERSONNELID")
    private Integer personnelid;
    @Basic(optional = false)
    @Column(name = "CONTACT")
    private String contact;
    @Basic(optional = false)
    @Column(name = "RELATIONSHIP")
    private String relationship;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATECODE")
    private String statecode;
    @Column(name = "POSTALCODE")
    private String postalcode;
    @Column(name = "PHONE")
    private String phone;
    @JoinColumn(name = "PERSONNELID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Login login;

    public Emergencycontacts() {
    }

    public Emergencycontacts(Integer personnelid) {
        this.personnelid = personnelid;
    }

    public Emergencycontacts(Integer personnelid, String contact, String relationship) {
        this.personnelid = personnelid;
        this.contact = contact;
        this.relationship = relationship;
    }

    public Integer getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(Integer personnelid) {
        this.personnelid = personnelid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personnelid != null ? personnelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emergencycontacts)) {
            return false;
        }
        Emergencycontacts other = (Emergencycontacts) object;
        if ((this.personnelid == null && other.personnelid != null) || (this.personnelid != null && !this.personnelid.equals(other.personnelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Emergencycontacts[ personnelid=" + personnelid + " ]";
    }
    
}
