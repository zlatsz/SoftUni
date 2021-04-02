import "./index.css";
import { Component } from 'react';
import * as productsService from '../../../services/productsService';
import Product from "../../Product/ProductView";

class Products extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            currentCategory: 'all',
        }
    }

    componentDidMount() {
        this.mounted = true;
       productsService.getAll()
            .then(res => this.setState({ products: res }));
    }

    componentWillUnmount() {
        this.mounted = false;
    }


    render() {
        return (
            <section className="home-products">
                <ul className="home-products-list">
                    {this.state.products.map(x =>
                        <Product key={x.id} {...x} />
                    )}
                </ul>
            </section>
        );
    }
}

export default Products;
