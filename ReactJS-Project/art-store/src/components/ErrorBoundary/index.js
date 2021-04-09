import React from 'react';

class MyErrorBoundary extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            error: null
        };
    }

    static getDerivedStateFromError(error) {
        // Update state so next render shows fallback UI.
        return { error: error }
    }

    componentDidCatch(error, info) {
        // Log the error to an error reporting service
        console.log('Error from componentDidCatch: ', error);
    }


    render() {
        if (this.state.error) {
            // You can render any custom fallback UI
            return <h3>Ооооопс, нещо се счупи! Дай назад!</h3>
        }
        return this.props.children;
    }
}

export default MyErrorBoundary;






