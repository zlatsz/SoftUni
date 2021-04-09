import { useState } from "react";
import { useHistory } from 'react-router-dom';
import * as userService from '../../../services/userService';
import "./index.css";

const ContactForm = () => {
  const history = useHistory();

  const contact = (e) => {
    e.preventDefault();

    userService.create({ ...formValues })
      .then(() => {
        history.push('/');
      })
      .catch((error) => alert(error.message));
  }

  // Simple controlled form setup
  const handleChange = e => setFormValues({ ...formValues, [e.target.name]: e.target.value })
  const [formValues, setFormValues] = useState({
    name: '',
    message: ''
  })

  return (

    <form className="contact-page-content" onSubmit={contact} >
      <input type="text" name="name" value={formValues.name} onChange={handleChange} required placeholder="Име"/>
      <textarea name="message" value={formValues.message} onChange={handleChange} required placeholder="Съобщение"/>
      <button type="submit" className="login-page-submit" >Прати</button>
    </form>
  )
}

export default ContactForm;