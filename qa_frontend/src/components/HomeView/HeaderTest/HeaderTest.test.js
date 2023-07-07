import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import HeaderTest from './HeaderTest';


describe('HeaderTest', () => {

    beforeEach(() => {
        window.matchMedia = jest.fn().mockImplementation((query) => ({
            matches: false,
            media: query,
            onchange: null,
            addListener: jest.fn(),
            removeListener: jest.fn(),
            addEventListener: jest.fn(),
            removeEventListener: jest.fn(),
            dispatchEvent: jest.fn(),
        }));
    });

    test('renders the component', () => {
        render(
            <Router>
                <HeaderTest />
            </Router>
        );

        // Check if the component renders without errors
        const LOGO = screen.getByTestId('logo');
        expect(LOGO).toBeInTheDocument();

        const menu=screen.getByTestId('menu');
        expect(menu).toBeInTheDocument();

        const search=screen.getByTestId('search');
        expect(search).toBeInTheDocument();

        const hello=screen.getByTestId('hello');
        expect(hello).toBeInTheDocument();

        const avatar=screen.getByTestId('avatar');
        expect(avatar).toBeInTheDocument();

        const log = screen.getByTestId('logout');
        expect(log).toBeInTheDocument();

    });

    test('clicking the search button calls the handleSearch function', () => {
        const handleSearch = jest.fn();

        render(
            <Router>
                <HeaderTest   handleSearch={handleSearch} />
            </Router>
        );

        const searchInput = screen.getByTestId('search');
        fireEvent.change(searchInput, { target: { value: 'test' } });
        fireEvent.keyPress(searchInput, { key: 'Enter', code: 13, charCode: 13 });
        expect(handleSearch).toHaveBeenCalledTimes(1);

    });



});
