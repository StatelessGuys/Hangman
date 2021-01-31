import './App.css';
import React from 'react';


class MenuList extends React.Component
{
  render()
  {
    return (
      <div className="menu-list">
        <li>
          <button className="my-button" onClick={this.props.onNewGameClick}>New Game</button>
          <button className="my-button">About</button>
        </li>
      </div>
    );
  }
}

class AlphabetTable extends React.Component
{
  constructor()
  {
    super();

    const alphabet = [];

    for (let i = 65; i < 90; i += 3)
    {
      const subArray = [];
      
      subArray.push({letter: String.fromCharCode(i), isHidden: false});
      subArray.push({letter: String.fromCharCode(i + 1), isHidden: false});
      subArray.push({letter: String.fromCharCode(i + 2), isHidden: false});
    
      alphabet[i] = (subArray);
    }

    alphabet[alphabet.length - 1][2].isHidden = true;

    this.state = {alphabet};
  }

  hideLetter = (row, column) =>
  {
    const {alphabet} = this.state;
    alphabet[row][column].isHidden = true;
    this.setState({alphabet});
  }

  render()
  {
    return (
      <table className="alphabet-table">
      {this.state.alphabet.map((element, index) => (
        <tr key={index}>
          <td><button className={this.state.alphabet[index][0].isHidden ? "hide-element " : "" + "letter-button"} onClick={ ()=>{this.props.onLetterClick(element[0].letter); this.hideLetter(index, 0);} }>{element[0].letter}</button></td>
          <td><button className={this.state.alphabet[index][1].isHidden ? "hide-element " : "" + "letter-button"} onClick={ ()=>{this.props.onLetterClick(element[1].letter); this.hideLetter(index, 1);} }>{element[1].letter}</button></td>
          <td><button className={this.state.alphabet[index][2].isHidden ? "hide-element " : "" + "letter-button"} onClick={ ()=>{this.props.onLetterClick(element[2].letter); this.hideLetter(index, 2);} }>{element[2].letter}</button></td>
        </tr>
      ))}
      </table>
    );
  }
}

class LetterCell extends React.Component
{
  constructor(props)
  {
    super(props);
  }

  render()
  {
    return (
      <div className="letter-cell">
        <label className="letter">{this.props.isHidden ? "" : this.props.letter}</label>              
        <div className="letter-underscore"></div>
      </div>
    );
  }
}

class HiddenWord extends React.Component
{
  constructor(props)
  {
    super(props);

    const letterCount = props.letterCount;

    const letterArray = [];
    for (let i = 0; i < letterCount; ++i)
    {
      letterArray.push({ letter: "", isHidden: true });
    }

    this.state = { letterArray };
  }

  updateWord = (letter, positions) =>
  {
    const letterArray = this.state.letterArray;    

    for (let i = 0; i < positions.length; ++i)
    {
      letterArray[positions[i]].letter = letter;
      letterArray[positions[i]].isHidden = false;
    }

    this.setState({ letterArray });
  }

  render()
  {
    return (
      <div className="hidden-word">
      { this.state.letterArray.map((element) => 
      {
        return <LetterCell letter={element.letter} isHidden={element.isHidden} />;
      }) }
      </div>
    );
  }
}

class Game extends React.Component
{
  constructor()
  {
    super();

    this.hiddenWord = React.createRef();

    this.state = { wordHash: "121512", letterCount: 8, imageName: 1 };

    this.imageFolder = "/images/";
    this.imageExt = ".png";
  }

  componentDidMount()
  {
    if (false)
    {
      fetch("/getRandomWord", { method: 'get' })
        .then(response => { if (!response.ok) throw "server error"; return response.text();})  
        .then(response => this.setState({ wordHash: response.wordHash, letterCount: response.letterCount}))
        .catch(error => alert(error));      
    }
  }

  getCurrentImageState = () =>
  {
    return window.location.origin + this.imageFolder + this.state.imageName + this.imageExt;
  }

  hangUpMan = () =>
  {
    this.setState({imageName: this.state.imageName + 1});
  }

  onLetterClick = (letter) =>
  {
    const body = { wordHash: this.state.wordHash, letter};
    if (false)
    {
      fetch("/getRandomWord", 
      {
        method: 'post',
        body: JSON.stringify(body)
      })
        .then(response => { if (!response.ok) throw "server error"; return response.text();})  
        .then(response => 
          {
            if (response.length != 0)
            {
              this.hiddenWord.current.updateWord(letter, response);
            }
            else
            {
              this.hangUpMan();
            }            
          })
        .catch(error => alert(error));    
    }

    this.hiddenWord.current.updateWord(letter, [0,1]);
  }

  render()
  {
    return (
      <div className="game">
        <div className="first-part">
          <img className="img" alt={"hangman"} src={this.getCurrentImageState()} />
          <AlphabetTable onLetterClick={this.onLetterClick} />
        </div>
        <div className="second-part">
          <HiddenWord ref={this.hiddenWord} letterCount={this.state.letterCount}/>             
        </div>
      </div>
    );
  }
}

class App extends React.Component
{
  constructor()
  {
    super();

    this.onNewGameClick = this.onNewGameClick.bind(this);

    this.state = { 
                    activeComponent: "game",
                    components: { menu: <MenuList onNewGameClick={this.onNewGameClick}/>, game: <Game /> }
                 };
  }

  onNewGameClick()
  {
    this.setState({ activeComponent: "game" });
  }

  render()
  {
    return (
      <div className="App">
        { this.state.components[this.state.activeComponent] }
      </div>
    );
  }
}

export default App;
