import "./index.css"
import { useState, useContext } from 'react'
import { Link, useHistory } from 'react-router-dom';
import { AuthContext } from '../../contexts/authentication';
import firebase from '../../firebase';
import Footer from "../Landing-page/Footer"

const Login = () => {
    const history = useHistory();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const signIn = (e) => {
        e.preventDefault();

        firebase.auth()
            .signInWithEmailAndPassword(email, password)
            .catch((error) => alert(error.message));

        // history.push('/home');
    }

    const { currentUser } = useContext(AuthContext);

    if (currentUser) {
        history.push('/home')
    }

    return (
        <>
            <section className="login-page">
                <Link to="/" className="login-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                <article className="login-page-wrapper">
                    <h2>Влез в профила си:</h2>
                    <form className="login-page-content" onSubmit={signIn}>
                        <i className="fas fa-envelope fa-lg" aria-hidden="true"></i>
                        <input type="text" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />

                        <i className="fa fa-lock fa-lg" aria-hidden="true"></i>
                        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />

                        <button type="submit" className="login-page-submit" value="">Влез</button>
                    </form>
                    <h3>Регистрирай се <Link to="/register"><span>ТУК</span></Link></h3>
                </article>
            </section>
            <Footer />
        </>
    );
};


export default Login;